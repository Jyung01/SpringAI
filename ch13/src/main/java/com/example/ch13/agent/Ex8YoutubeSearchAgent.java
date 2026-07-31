package com.example.ch13.agent;

import java.util.ArrayList;
import java.util.List;

import com.example.ch13.dto.Youtube;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


// YouTube 비디오 검색 에이전트
// SerpApi YouTube 검색을 사용하여 여행 장소 관련 비디오 검색 및 메타데이터 제공
// SerpApi를 통해 YouTube 데이터에 접근
@Component
public class Ex8YoutubeSearchAgent {
  //-----------------------------------------------------------------------------------
  // SerpApi 엔드포인트와 API 키
  private final String serpApiKey;
  // WebClient: SerpApi 요청에 사용
  private final WebClient webClient;
  // JSON 파서로 사용
  private final ObjectMapper objectMapper = new ObjectMapper();

  public Ex8YoutubeSearchAgent(
      @Value("${serpapi.endpoint}") String serpApiEndpoint,
      @Value("${serpapi.apiKey}") String serpApiKey,
      WebClient.Builder webClientBuilder) {
    this.serpApiKey = serpApiKey;

    // WebClient 설정: SerpApi 베이스 URL
    this.webClient = webClientBuilder
        .baseUrl(serpApiEndpoint)
        .defaultHeader("Accept", "application/json")
        .build();

  }

  //-----------------------------------------------------------------------------------
  // SerpApi 검색 결과를 직접 DTO 목록으로 변환하여 반환

  public List<Youtube> execute(String userQuery) {
    if (userQuery == null || userQuery.isBlank()) {
      return List.of();
    }

    try {
      String responseBody = requestYoutubeSearch(userQuery.trim());
      if (responseBody == null || responseBody.isBlank()) {
        return List.of();
      }

      JsonNode videoResults = objectMapper.readTree(responseBody).path("video_results");
      if (!videoResults.isArray() || videoResults.isEmpty()) {
        return List.of();
      }

      List<Youtube> youtubeList = new ArrayList<>();
      for (JsonNode video : videoResults) {
        String title = video.path("title").asText("");
        String link = video.path("link").asText("");
        String publishedDate = video.path("published_date").asText("날짜 정보 없음");

        if (!title.isBlank() && isYoutubeVideoLink(link)) {
          Youtube youtube = new Youtube();
          youtube.setTitle(title);
          youtube.setUploadDate(publishedDate.isBlank() ? "날짜 정보 없음" : publishedDate);
          youtube.setLink(link);
          youtubeList.add(youtube);
        }

        // 화면에 너무 많은 카드가 출력되지 않도록 상위 10개만 반환
        if (youtubeList.size() == 10) {
          break;
        }
      }

      return youtubeList;
    } catch (Exception e) {
      throw new IllegalStateException("YouTube 검색 중 오류가 발생했습니다: " + e.getMessage(), e);
    }
  }

  private String requestYoutubeSearch(String query) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("engine", "youtube")
            .queryParam("search_query", query)
            .queryParam("api_key", serpApiKey)
            .build())
        .retrieve()
        .onStatus(status -> status.value() == 401 || status.value() == 403,
            response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("SerpApi API 키 또는 사용 권한을 확인하세요.")))
        .onStatus(status -> status.value() == 429,
            response -> response.bodyToMono(String.class)
                .map(body -> new RuntimeException("SerpApi 요청 한도를 초과했습니다.")))
        .bodyToMono(String.class)
        .block();
  }

  private boolean isYoutubeVideoLink(String link) {
    return link != null
        && (link.startsWith("https://www.youtube.com/watch?v=")
            || link.startsWith("https://youtu.be/")
            || link.startsWith("https://www.youtube.com/shorts/"));
  }

  //-----------------------------------------------------------------------------------
  // Tool 메소드: YouTube 비디오 검색
  @Tool(description = """
      YouTube에서 여행 관련 비디오를 검색합니다.
      검색 키워드를 입력하면 관련 비디오의 정보를 JSON 형식으로 제공합니다.
      각 비디오의 제목, 업로드 날짜, 링크가 포함됩니다.
      """)
  public String searchYoutubeVideos(
      @ToolParam(description = "검색 키워드 (예: '서울 여행', '부산 맛집')") String query) {

    try {
      String responseBody = requestYoutubeSearch(query);

      if (responseBody == null) {
        return String.format("'%s'에 대한 검색 결과를 가져올 수 없습니다.", query);
      }

      // JSON 파싱 및 결과 포맷팅
      JsonNode root = objectMapper.readTree(responseBody);
      JsonNode videoResults = root.path("video_results");

      if (!videoResults.isArray() || videoResults.isEmpty()) {
        return String.format("'%s'에 대한 검색 결과가 없습니다.", query);
      }

      // 결과를 JSON 형식으로 포맷팅하여 반환
      return formatVideosAsJson(videoResults);

    } catch (Exception e) {
      return "YouTube 검색 오류: " + e.getMessage();
    }
  }

  //-----------------------------------------------------------------------------------
  // 비디오 정보를 JSON 배열 형식으로 포맷팅
  private String formatVideosAsJson(JsonNode videoResults) {
    List<String> videos = new ArrayList<>();

    for (JsonNode video : videoResults) {
      String title = video.path("title").asText("").replace("\"", "\\\"");
      String link = video.path("link").asText("");
      String publishedDate = video.path("published_date").asText("");

      if (!title.isEmpty() && !link.isEmpty()) {
        String videoJson = String.format(
            "{\"title\":\"%s\",\"uploadDate\":\"%s\",\"link\":\"%s\"}",
            title,
            publishedDate.isEmpty() ? "날짜 정보 없음" : publishedDate,
            link
        );
        videos.add(videoJson);
      }
    }

    return "[" + String.join(",", videos) + "]";
  }
}
const ollamaVoice = (() => {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

    function recognize({ onStart, onResult, onError }) {
        if (!SpeechRecognition) {
            onError('이 브라우저는 음성 인식을 지원하지 않습니다. Chrome 또는 Edge를 사용해 주세요.');
            return;
        }

        const recognition = new SpeechRecognition();
        recognition.lang = 'ko-KR';
        recognition.interimResults = false;
        recognition.continuous = false;

        recognition.onstart = onStart;
        recognition.onresult = event => onResult(event.results[0][0].transcript);
        recognition.onerror = event => onError(`음성 인식 오류: ${event.error}`);
        recognition.start();
    }

    function speak(text) {
        if (!('speechSynthesis' in window) || !text.trim()) return;

        speechSynthesis.cancel();
        const utterance = new SpeechSynthesisUtterance(text);
        utterance.lang = 'ko-KR';
        utterance.rate = 1;

        const voices = speechSynthesis.getVoices();
        const koreanVoice = voices.find(voice => voice.lang.toLowerCase().startsWith('ko'));
        if (koreanVoice) utterance.voice = koreanVoice;

        speechSynthesis.speak(utterance);
    }

    function stopSpeaking() {
        if ('speechSynthesis' in window) speechSynthesis.cancel();
    }

    async function streamChat(question, onChunk) {
        const response = await fetch('/ai/chat-text', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Accept': 'text/event-stream'
            },
            body: new URLSearchParams({ question })
        });

        if (!response.ok || !response.body) {
            const message = await response.text();
            throw new Error(message || `HTTP ${response.status}`);
        }

        const reader = response.body.getReader();
        const decoder = new TextDecoder();
        let buffer = '';
        let answer = '';

        while (true) {
            const { done, value } = await reader.read();
            buffer += decoder.decode(value || new Uint8Array(), { stream: !done });

            const events = buffer.split('\n\n');
            buffer = events.pop() || '';

            for (const event of events) {
                const chunk = event.split('\n')
                    .filter(line => line.startsWith('data:'))
                    .map(line => line.slice(5).trimStart())
                    .join('\n');

                if (chunk && chunk !== '[DONE]') {
                    answer += chunk;
                    onChunk(chunk);
                }
            }

            if (done) break;
        }

        if (buffer.startsWith('data:')) {
            const chunk = buffer.slice(5).trimStart();
            answer += chunk;
            onChunk(chunk);
        }

        return answer;
    }

    return { recognize, speak, stopSpeaking, streamChat };
})();

import askAI from "./apiService.js";

export async function handleChatSubmit(event) {
    event.preventDefault();

    const form = new FormData(event.target);
    const prompt = form.get("prompt");

    if (!prompt || prompt.trim() === "") {
        return;
    }

    // Vis brugerens besked
    addToChat(prompt.trim(), "user");

    // ryd tekstfelt
    event.target.reset();

    try {
        const data = await askAI(prompt.trim());
        // forventer backend svarer med { response: "..." }
        addToChat(data.response ?? "No response from server", "assistant");
    } catch (err) {
        console.error(err);
        addToChat("Der skete en fejl, prøv igen", "assistant");
    }
}

export function addToChat(message, sender) {
    const messages = document.querySelector("#chat");
    const messageElement = document.createElement("div");
    messageElement.classList.add("message", sender);

    const bubble = document.createElement("div");
    bubble.classList.add("bubble");
    bubble.textContent = message;

    messageElement.appendChild(bubble);
    messages.appendChild(messageElement);

    // scroll til bunden
    messages.scrollTop = messages.scrollHeight;
}

export function handleKeyDown(e) {
    const form = document.getElementById("chat-form");
    if (e.key === "Enter" && !e.ctrlKey && !e.shiftKey) {
        e.preventDefault();   // ingen newline
        form.requestSubmit(); // submit formen
    }
}


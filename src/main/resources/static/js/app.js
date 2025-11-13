import { handleChatSubmit, handleKeyDown } from "./formSubmit.js";

window.addEventListener("DOMContentLoaded", initApp);

function initApp() {
    console.log("Movie Chatbot app initialized");
    setupEventListeners();
}

function setupEventListeners() {
    const chatForm = document.querySelector("#chat-form");
    chatForm.addEventListener("submit", handleChatSubmit);

    const textarea = chatForm.querySelector("textarea");
    textarea.addEventListener("keydown", handleKeyDown);
}

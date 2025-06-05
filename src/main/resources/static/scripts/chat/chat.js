document.getElementById('sendMessageBtn').addEventListener('click', () => {
    const form = document.getElementById('messageForm');
    const formData = new FormData(form);

    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    fetch('/messages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': data['${(_csrf.parameterName)!}']
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) throw new Error("Ошибка при отправке");
            return response.text();
        })
        .then(result => {
            document.getElementById('contentInput').value = "";
            console.log("Сообщение отправлено");
        })
        .catch(error => {
            console.error("Ошибка:", error);
        });
});



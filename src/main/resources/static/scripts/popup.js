function openForm() {
    var form = document.getElementById("myForm");
    form.style.display = "block"; // Делаем форму видимой
}

// Функция для закрытия формы
function closeForm() {
    var form = document.getElementById("myForm");
    form.style.display = "none"; // Скрываем форму
}

function submitForm() {
    var formData = new FormData(document.getElementById("formId"));

    fetch('/submitForm', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                // Действия при успешной отправке формы
                console.log("Форма успешно отправлена");
                closeForm(); // Закрываем форму
            } else {
                throw new Error('Ошибка при отправке формы');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
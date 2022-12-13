const imageItem = document.querySelectorAll(".image-item");

imageItem.forEach(function (element, index) {
  element.onclick = function () {
    const bigImageItem = document.querySelector(".image_product_big img");
    const imageItemActive = document.querySelector(".image-item.active");

    bigImageItem.setAttribute("src", element.children[0].getAttribute("src"));

    imageItemActive.classList.remove("active");
    element.classList.add("active");
  };
});

function checkPasswordMatch(fieldConfirmPassword) {
  if (fieldConfirmPassword.value != $("#password").val()) {
    fieldConfirmPassword.setCustomValidity("Passwords do not match!");
  } else {
    fieldConfirmPassword.setCustomValidity("");
  }
}

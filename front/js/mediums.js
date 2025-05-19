document.addEventListener("DOMContentLoaded", () => {
  const openButtons = document.querySelectorAll(".open-popup");
  const closeButtons = document.querySelectorAll(".close-popup");

  openButtons.forEach(btn => {
    btn.addEventListener("click", () => {
      const targetId = btn.getAttribute("data-popup");
      document.getElementById(targetId).style.display = "flex";
    });
  });

  closeButtons.forEach(btn => {
    btn.addEventListener("click", () => {
      btn.closest(".popup").style.display = "none";
    });
  });
});


    document.getElementById("mybutton").addEventListener("click", function() {
    // 获取表格的tbody元素
    var tableBody = document.querySelector(".table tbody");

    // 将表格行转换为数组
    var rows = Array.from(tableBody.getElementsByTagName("tr"));

    // 对表格行进行排序
    rows.sort(function(a, b) {
    var timeCodeA = parseInt(a.cells[2].textContent);
    var timeCodeB = parseInt(b.cells[2].textContent);
    return timeCodeB - timeCodeA;
});

    // 清空表格内容
    tableBody.innerHTML = "";

    // 将排序后的行重新添加到表格中
    rows.forEach(function(row) {
    tableBody.appendChild(row);
});
});


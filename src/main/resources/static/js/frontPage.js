


function refresh() {
    var buy_table  = document.getElementById("buyTable");
    var sell_table = document.getElementById("sellTable");

    var rowSell = document.getElementsByTagName('tbody')[0];
    var rowBuy = document.getElementsByTagName('tbody')[1];
    rowSell.parentNode.removeChild(rowSell);
    rowBuy.parentNode.removeChild(rowBuy);

    var new_tbody_sell = document.createElement('tbody');
    sell_table.appendChild(new_tbody_sell);

    var new_tbody_buy = document.createElement('tbody');
    buy_table.appendChild(new_tbody_buy);

    var jqxhr = $.ajax({
        url: '/api/transaction/sells',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function(data) {
            $.each(data, function(index, element) {
                var sum = element.price*element.amount;
                sum = Math.round(sum * 1000) / 1000;
                addToTable(sell_table,element.price,element.amount,sum);
            });
        }
    });
    var jqxhr = $.ajax({
        url: '/api/transaction/buys',
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function(data) {
            $.each(data, function(index, element) {
                var sum = element.price*element.amount;
                sum = Math.round(sum * 1000) / 1000;
                addToTable(buy_table,element.price,element.amount,sum);
            });
        }
    });

};

function addToTable(table, value1, value2, value3) {

    var tr = document.createElement("tr");

    var td = document.createElement("td");
    var txt = document.createTextNode(value1);

    var td2 = document.createElement("td");
    var txt2 = document.createTextNode(value2);


    var td3 = document.createElement("td");
    var txt3 = document.createTextNode(value3);

    td.appendChild(txt);
    td2.appendChild(txt2);
    td3.appendChild(txt3);
    tr.appendChild(td);
    tr.appendChild(td2);
    tr.appendChild(td3);
    table.appendChild(tr);

}

//Refresh List when page is loaded
$( document ).ready(refresh);

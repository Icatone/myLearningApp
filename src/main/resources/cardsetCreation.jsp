<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <html>

    <head>
        <meta charset="UTF-8">
        <script>
            let cardCnt = 1;
            window.onload = onPageLoaded;
            window.onload = onPageLoaded;

            function onPageLoaded() {
                const reg_link = document.getElementById('registration_link');
                if (reg_link != undefined) {
                    localStorage.setItem('prev_link', window.location);
                }
            }
            function addCard() {
                cardCnt += 1;
                let newTr = document.createElement('tr');
                newTr.setAttribute('id', 'card_' + cardCnt);
                newTr.setAttribute('class', 'card_row');
                let newTd1 = document.createElement('td');
                newTd1.setAttribute("contenteditable", "true");
                let newTd2 = document.createElement('td');
                newTd2.setAttribute("contenteditable", "true");
                document.getElementById('buttons_row1').before(newTr);
                newTr.append(newTd2);
                newTr.append(newTd1);
            }
            function deleteCard() {
                let tr = document.getElementById('card_' + cardCnt);
                tr.remove();
                cardCnt -= 1;
            }
            function sendCards() {
                const cardsetName = document.getElementById('cardset_name').outerText;
                let isValid = true;
                if (cardsetName == '') {
                    alert('<c:out value="${strings[10]}"/>');
                }
                else if (cardsetName == '<c:out value="${strings[2]}"/>') {
                    alert('<c:out value="${strings[11]}"/>');
                }
                const rows = document.getElementsByClassName('card_row');

                for (let i = 0; i < rows.length; ++i) {
                    console.log(rows[i]);
                    if (rows[i].firstElementChild.innerText == '' || rows[i].lastElementChild.innerText == '') {
                        alert('<c:out value="${strings[12]}"/>');
                        return;
                    }
                }

                const cards = [];
                for (let i = 0; i < rows.length; ++i) {
                    let card = {
                        "question": rows[i].firstElementChild.innerText,
                        "answer": rows[i].lastElementChild.innerText
                    }
                    cards.push(card);
                }
                const cardset = {
                    "name": cardsetName,
                    "description": document.getElementById('cardset_description').innerText,
                    "cards": cards
                }

                const xhr = new XMLHttpRequest();
                xhr.onloadend = (event) => {
                    if(xhr.status==200) {
                        alert('<c:out value="${strings[13]}"/>');
                    }
                    else {
                        alert('<c:out value="${strings[14]}"/>');
                    }
                }
                xhr.open("POST", window.location, true);
                
                xhr.send(JSON.stringify(cardset));
            }
        </script>
    </head>

    <body>
        <a href="index.html"><c:out value="${strings[15]}"></c:out></a>
        <c:if test="${isLogged==false}">
            <a href="registration.html" id="registration_link">
                <c:out value="${strings[0]}"></c:out>
            </a>
        </c:if>
        <c:if test="${isLogged==true}">

            <h3>
                <c:out value="${strings[1]}"></c:out>
            </h3>
            <h3 contenteditable="true" id="cardset_name">
                <c:out value="${strings[2]}"></c:out>
            </h3>
            <h3>
                <c:out value="${strings[3]}"></c:out>
            </h3>
            <p contenteditable="true" id="cardset_description">
                <c:out value="${strings[4]}"></c:out>
            </p>
            <table>
                <tr id="headers_row">
                    <td>
                        <c:out value="${strings[5]}"></c:out>
                    </td>
                    <td>
                        <c:out value="${strings[6]}"></c:out>
                    </td>
                </tr>
                <tr id="card_1" class="card_row">
                    <td contenteditable="true">
                        <c:out value="${strings[5]}"></c:out>
                    </td>
                    <td contenteditable="true">
                        <c:out value="${strings[6]}"></c:out>
                    </td>
                </tr>
                <tr id="buttons_row1">
                    <td>
                        <button onclick="addCard()">
                            <c:out value="${strings[7]}"></c:out>
                        </button>
                    </td>
                    <td>
                        <button onclick="deleteCard()">
                            <c:out value="${strings[8]}"></c:out>
                        </button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button onclick="sendCards()">
                            <c:out value="${strings[9]}"></c:out>
                        </button>
                    </td>
                </tr>
            </table>
        </c:if>
    </body>

    </html>
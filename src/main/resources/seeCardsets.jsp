<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <html>

    <head>
        <meta charset="UTF-8">
        <style>
            .buttons_container {
                display: flex;
                flex-direction: row;
                flex-wrap: wrap;
                height: fit-content;
            }
        </style>
        <script>
            function toTraining(btn) {
                window.location.replace("cardTraining.html?cardset_id=" + btn.getAttribute("data-cardset_id"));
            }
            function toDelete(btn) {
                const xhr = new XMLHttpRequest();


                xhr.open("DELETE", window.location, true);
                xhr.setRequestHeader("cardset_id", btn.getAttribute("data-cardset_id"));
                xhr.onloadend = (event) => {
                    window.location.reload();
                }
                xhr.send();
            }
        </script>

    </head>

    <body>
        <a href="index.html"><c:out value="${strings[6]}"></c:out></a>
        <c:if test="${isLogged==true}">
            <h3>
                <c:out value="${strings[0]}"></c:out>
            </h3>
            <c:forEach var="user_cardset" items="${user_cardsets}">
                <div>

                    <h4>
                        <c:out value="${user_cardset.getName()}"></c:out>
                    </h4>
                    <p>
                        <c:out value="${user_cardset.getDescription()}"></c:out>
                    </p>
                    <div class="buttons_container">
                        <button onclick="toTraining(this)"
                            data-cardset_id='<c:out value="${user_cardset.getId()}"></c:out>'>
                            <c:out value="${strings[1]}"></c:out>
                        </button>
                        <button onclick="toDelete(this)"
                        data-cardset_id='<c:out value="${user_cardset.getId()}"></c:out>'>
                            <c:out value="${strings[3]}"></c:out>
                        </button>
                    </div>

                </div>
            </c:forEach>
        </c:if>
        <h3>
            <c:out value="${strings[5]}"></c:out>
        </h3>
        <c:forEach var="cardset" items="${cardsets}">
            <div>

                <h4>
                    <c:out value="${cardset.getName()}"></c:out>
                </h4>
                <p>
                    <c:out value="${cardset.getDescription()}"></c:out>
                </p>
                <h4>
                    <c:out value="${strings[4]}"></c:out>
                </h4>
                <div class="buttons_container">
                    <button onclick="toTraining(this)" data-cardset_id='<c:out value="${cardset.getId()}"></c:out>'>
                        <c:out value="${strings[1]}"></c:out>
                    </button>
                </div>

            </div>
        </c:forEach>
    </body>

    </html>
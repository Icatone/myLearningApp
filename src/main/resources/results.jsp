<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
    <head>
        <meta charset="UTF-8">
        <style>
            .row_true {
                background-color: seagreen;
            }
            .row_false {
                background-color: brown;
            }
        </style>
        <script>
            window.onload = onPageLoaded;

            function onPageLoaded(){
                const reg_link = document.getElementById('registration_link');
                if(reg_link!=undefined) {
                    localStorage.setItem('prev_link',window.location);
                }
            }
        </script>
    </head>
    <body>
        <a href="index.html"><c:out value="${strings[4]}"></c:out></a>
        <c:if test="${isLogged==false}">
            <a href="registration.html" id="registration_link"><c:out value="${strings[3]}"></c:out></a>
        </c:if>
        <table id="table_results">
            <tr>
                <td>
                    <c:out value="${strings[0]}"></c:out>
                </td>
                <td>
                    <c:out value="${strings[1]}"></c:out>
                </td>
                <td>
                    <c:out value="${strings[2]}"></c:out>
                </td>
            </tr>
            <c:forEach var="card_result" items="${answers}">
                <c:if test="${card_result.isAnswerRight() == true}">
                    <tr class="row_true">
                </c:if>
                <c:if test="${card_result.isAnswerRight() == false}">
                    <tr class="row_false">
                </c:if>
                    <td>
                        <c:out value="${card_result.getCard().getQuestion()}"></c:out>
                    </td>
                    <td>
                        <c:out value="${card_result.getAnswer()}"></c:out>
                    </td>
                    <td>
                        <c:out value="${card_result.getCard().getAnswer()}"></c:out>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
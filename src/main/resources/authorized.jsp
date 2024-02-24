<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>welcome</title>
    <script>
        const prev_link = localStorage.getItem("prev_link");
        localStorage.removeItem("prev_link");
        if(prev_link!=null){
            window.location.replace(prev_link);
        }
        
    </script>
</head>
<body>
    <h2>Welcome</h2>
</body>
</html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<style>
    form {
    padding: 12px 20px;
    }
    </style>
<title>Add user</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/users/new-user" method="post">
    <div class="form-group col-md-3">
        <label for="username">Username</label>
        <input type="text" class="form-control" name="username" required maxlength="40">
      </div>
    <div class="form-group col-md-3">
        <label for="password">Password</label>
        <input type="text" class="form-control" name="password" required maxlength="40">
    </div>
    <div class="form-group col-md-3">
        <label for="age">Age</label>
        <input type="number" class="form-control" name="age" required min="0">
    </div>
    <div class="form-group col-md-3">
        <label for="address">Address</label>
        <input type="text" class="form-control" name="address" maxlength="100">
    </div>
    <div class="form-group col-md-3">
        <label for="telephone">Telephone</label>
        <input type="text" class="form-control" name="telephone" maxlength="40">
    </div>
    <div class="form-check">
          <input class="form-check-input" type="checkbox" name="isActive">
                <label class="form-check-label" for="isActive">
                    Check if active
                </label>
    </div>
    <br>
    <button type="submit" class="btn btn-outline-success">Add user</button>
</form>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>
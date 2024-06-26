<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Utilisateurs
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="last_name" name="last_name" value="${not empty last_name ? last_name : ''}" placeholder="Nom" required>
                                        <span id="last_name_error" class="text-danger">${nomError}</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prenom</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="first_name" name="first_name" value="${not empty first_name ? first_name : ''}" placeholder="Prenom" required>
                                        <span id="first_name_error" class="text-danger">${prenomError}</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="email" name="email" value="${not empty email ? email : ''}" placeholder="Email" required>
                                        <span id="email_error" class="text-danger">${emailError}</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birth_date" class="col-sm-2 control-label">Date de naissance</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="birth_date" name="birth_date" value="${not empty birth_date ? birth_date : ''}" placeholder="Date de naissance" required>
                                        <span id="birthdate_error" class="text-danger">${birthdateError}</span> <!-- Error message -->
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                            <!-- /.box-footer -->
                            <!-- /.box-footer -->
                        </form>

                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>

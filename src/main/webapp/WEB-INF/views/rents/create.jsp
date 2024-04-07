<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
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
                Reservations
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
                                    <label for="client" class="col-sm-2 control-label">Client</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client">
                                            <c:forEach items="${clients}" var="client">
                                                <option value="${client.id}">${client.id} ${client.nom} ${client.prenom}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="car" class="col-sm-2 control-label">Voiture</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="car" name="car">
                                            <c:forEach items="${vehicles}" var="vehicle">
                                                <option value="${vehicle.id}">${vehicle.id} ${vehicle.constructeur} ${vehicle.modele}</option>
                                            </c:forEach>
                                        </select>
                                        <span id="vehicle_error" class="text-danger">${vehicle_error}</span>
                                        <c:if test="${not empty vehicle_error}"><br></c:if>
                                        <span id="vehicle_error_3" class="text-danger">${vehicle_error_3}</span>
                                        <c:if test="${not empty vehicle_error_3}"><br></c:if>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Date de debut</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="begin" name="begin" required>
                                        <span id="begin_error" class="text-danger">${begin_error}</span>
                                        <c:if test="${not empty begin_error}"><br></c:if>
                                        <span id="begin_error_2" class="text-danger">${begin_error_2}</span>
                                        <c:if test="${not empty begin_error_2}"><br></c:if>
                                        <span id="begin_error_3" class="text-danger">${begin_error_3}</span>
                                        <c:if test="${not empty begin_error_3}"><br></c:if>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">Date de fin</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="end" name="end" required>
                                        <span id="end_error" class="text-danger">${end_error}</span>
                                        <c:if test="${not empty end_error}"><br></c:if>
                                        <span id="end_error_2" class="text-danger">${end_error_2}</span>
                                        <c:if test="${not empty end_error_2}"><br></c:if>
                                        <span id="end_error_3" class="text-danger">${end_error_3}</span>
                                        <c:if test="${not empty end_error_3}"><br></c:if>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
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
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>
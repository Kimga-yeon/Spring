/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.56
 * Generated at: 2020-08-14 01:03:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/lib/taglibs-standard-impl-1.2.5 (1).jar", Long.valueOf(1597285716256L));
    _jspx_dependants.put("jar:file:/C:/workspace/framework/springProject/src/main/webapp/WEB-INF/lib/taglibs-standard-impl-1.2.5%20(1).jar!/META-INF/c.tld", Long.valueOf(1425946270000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n");
      out.write("<title>Spring Project</title>\r\n");
      out.write("<link rel=\"canonical\" href=\"https://getbootstrap.com/docs/4.5/examples/carousel/\">\r\n");
      out.write("<style>\r\n");
      out.write(".bd-placeholder-img {\r\n");
      out.write("\tfont-size: 1.125rem;\r\n");
      out.write("\ttext-anchor: middle;\r\n");
      out.write("\t-webkit-user-select: none;\r\n");
      out.write("\t-moz-user-select: none;\r\n");
      out.write("\t-ms-user-select: none;\r\n");
      out.write("\tuser-select: none;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("@media ( min-width : 768px) {\r\n");
      out.write("\t.bd-placeholder-img-lg {\r\n");
      out.write("\t\tfont-size: 3.5rem;\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<!-- Custom styles for this template -->\r\n");
      out.write("<link href=\"resources/css/carousel.css\" rel=\"stylesheet\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<!-- jsp 액션 태그를 이용한 동적 include -->\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "WEB-INF/views/common/header.jsp", out, false);
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<main role=\"main\">\r\n");
      out.write("\r\n");
      out.write("\t<!-- 슬라이드 효과 부트스트랩 -->\r\n");
      out.write("\t<div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">\r\n");
      out.write("\t\t<ol class=\"carousel-indicators\">\r\n");
      out.write("\t\t\t<li data-target=\"#myCarousel\" data-slide-to=\"0\" class=\"active\"></li>\r\n");
      out.write("\t\t\t<!-- <li data-target=\"#myCarousel\" data-slide-to=\"1\"></li> \r\n");
      out.write("\t\t\t<li data-target=\"#myCarousel\" data-slide-to=\"2\"></li> -->\r\n");
      out.write("\t\t</ol>\r\n");
      out.write("\t\t<div class=\"carousel-inner\">\r\n");
      out.write("\t\t\t<div class=\"carousel-item active\">\r\n");
      out.write("\t\t\t\t<svg class=\"bd-placeholder-img\" width=\"100%\" height=\"100%\" xmlns=\"http://www.w3.org/2000/svg\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\">\r\n");
      out.write("\t\t\t\t\t<rect width=\"100%\" height=\"100%\" fill=\"#bbb\" /></svg>\r\n");
      out.write("\t\t\t\t<div class=\"container\">\r\n");
      out.write("\t\t\t\t\t<div class=\"carousel-caption text-left\">\r\n");
      out.write("\t\t\t\t\t\t<h1>Spring Project 입니다.</h1>\r\n");
      out.write("\t\t\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t\t\tSpring MVC 프로젝트를 이용해서 개발되고 있습니다.\r\n");
      out.write("\t\t\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t\t\t<a class=\"btn btn-lg btn-primary\" href=\"#\" role=\"button\">View details</a>\r\n");
      out.write("\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<!-- 이전 화살표 -->\r\n");
      out.write("\t\t<a class=\"carousel-control-prev\" href=\"#myCarousel\" role=\"button\"\r\n");
      out.write("\t\t\tdata-slide=\"prev\"> <span class=\"carousel-control-prev-icon\"\r\n");
      out.write("\t\t\taria-hidden=\"true\"></span> <span class=\"sr-only\">Previous</span>\r\n");
      out.write("\t\t<!-- 다음 화살표 -->\r\n");
      out.write("\t\t</a> <a class=\"carousel-control-next\" href=\"#myCarousel\" role=\"button\"\r\n");
      out.write("\t\t\tdata-slide=\"next\"> <span class=\"carousel-control-next-icon\"\r\n");
      out.write("\t\t\taria-hidden=\"true\"></span> <span class=\"sr-only\">Next</span>\r\n");
      out.write("\t\t</a>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t<!-- Marketing messaging and featurettes\r\n");
      out.write("  ================================================== --> <!-- Wrap the rest of the page in another container to center all the content. -->\r\n");
      out.write("\r\n");
      out.write("\t<div class=\"container marketing\">\r\n");
      out.write("\r\n");
      out.write("\t\t<!-- Three columns of text below the carousel -->\r\n");
      out.write("\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t<div class=\"col-lg-4\">\r\n");
      out.write("\t\t\t\t<svg class=\"bd-placeholder-img rounded-circle\" width=\"140\" height=\"140\" xmlns=\"http://www.w3.org/2000/svg\"\r\n");
      out.write("\t\t\t\t\tpreserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\"aria-label=\"Placeholder: 140x140\">\r\n");
      out.write("\t\t\t\t\t<title>Placeholder</title><rect width=\"100%\" height=\"100%\"fill=\"#777\" />\r\n");
      out.write("\t\t\t\t\t<text x=\"50%\" y=\"50%\" fill=\"#777\" dy=\".3em\">140x140</text></svg>\r\n");
      out.write("\t\t\t\t<h2>Heading</h2>\r\n");
      out.write("\t\t\t\t<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis\r\n");
      out.write("\t\t\t\t\teuismod. Nullam id dolor id nibh ultricies vehicula ut id elit.\r\n");
      out.write("\t\t\t\t\tMorbi leo risus, porta ac consectetur ac, vestibulum at eros.\r\n");
      out.write("\t\t\t\t\tPraesent commodo cursus magna.</p>\r\n");
      out.write("\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t<a class=\"btn btn-secondary\" href=\"#\" role=\"button\">View details &raquo;</a>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- /.col-lg-4 -->\r\n");
      out.write("\t\t\t<div class=\"col-lg-4\">\r\n");
      out.write("\t\t\t\t<svg class=\"bd-placeholder-img rounded-circle\" width=\"140\" height=\"140\" xmlns=\"http://www.w3.org/2000/svg\"\r\n");
      out.write("\t\t\t\t\tpreserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: 140x140\">\r\n");
      out.write("\t\t\t\t\t<title>Placeholder</title><rect width=\"100%\" height=\"100%\" fill=\"#777\" />\r\n");
      out.write("\t\t\t\t\t<text x=\"50%\" y=\"50%\" fill=\"#777\" dy=\".3em\">140x140</text></svg>\r\n");
      out.write("\t\t\t\t<h2>Heading</h2>\r\n");
      out.write("\t\t\t\t<p>Duis mollis, est non commodo luctus, nisi erat porttitor\r\n");
      out.write("\t\t\t\t\tligula, eget lacinia odio sem nec elit. Cras mattis consectetur\r\n");
      out.write("\t\t\t\t\tpurus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo,\r\n");
      out.write("\t\t\t\t\ttortor mauris condimentum nibh.</p>\r\n");
      out.write("\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t<a class=\"btn btn-secondary\" href=\"#\" role=\"button\">View details &raquo;</a>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- /.col-lg-4 -->\r\n");
      out.write("\t\t\t<div class=\"col-lg-4\">\r\n");
      out.write("\t\t\t\t<svg class=\"bd-placeholder-img rounded-circle\" width=\"140\" height=\"140\" xmlns=\"http://www.w3.org/2000/svg\"\r\n");
      out.write("\t\t\t\t\tpreserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: 140x140\">\r\n");
      out.write("\t\t\t\t\t<title>Placeholder</title><rect width=\"100%\" height=\"100%\" fill=\"#777\" />\r\n");
      out.write("\t\t\t\t\t<text x=\"50%\" y=\"50%\" fill=\"#777\" dy=\".3em\">140x140</text></svg>\r\n");
      out.write("\t\t\t\t<h2>Heading</h2>\r\n");
      out.write("\t\t\t\t<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,\r\n");
      out.write("\t\t\t\t\tegestas eget quam. Vestibulum id ligula porta felis euismod semper.\r\n");
      out.write("\t\t\t\t\tFusce dapibus, tellus ac cursus commodo, tortor mauris condimentum\r\n");
      out.write("\t\t\t\t\tnibh, ut fermentum massa justo sit amet risus.</p>\r\n");
      out.write("\t\t\t\t<p>\r\n");
      out.write("\t\t\t\t\t<a class=\"btn btn-secondary\" href=\"#\" role=\"button\">View details &raquo;</a>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- /.col-lg-4 -->\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- /.row -->\r\n");
      out.write("\t</main>\r\n");
      out.write("\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "WEB-INF/views/common/footer.jsp", out, false);
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

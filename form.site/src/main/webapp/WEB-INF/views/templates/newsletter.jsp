<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <jsp:include page="../common/head.jsp"/>
        <style type="text/css"> 
            body {
                background: url('/static/ladylike/images/xs/Ladylike-logo-660.png') no-repeat top center fixed;
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
                height:100%;
                width:100%;
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
            }
            .logo {
                width: 100%;
                text-align: center;
                height: 100%;
                margin-top: 170px;
            }
            .logo_img {
                width:232px;
            }
            button, input, select, textarea {
                border: none;
            }
            #mc-embedded-subscribe {
                color:#fff;
                background:#ff5050;
                padding:10px;
            }
            #mc-embedded-subscribe {
                background-color:#ff5050;
                width: 105px;
                height: 34px;
                font-family: 'Open Sans';
                color:  #ffffff;
                font-size: 13px;
                font-weight: 300;
                text-align: center;
                transform: scaleX(1.0002);
                margin-top:20px;
                padding:8px 20px 12px;
            }
            #mce-EMAIL {
                background-color:  #ffffff;
                width: 232px;
                height: 40px;
                margin-top: 45px;
                font-family: 'Open Sans';
                color:  #999999;
                font-size: 13px;
                font-style: italic;
                text-align: center;
            }
            .back {
                position: absolute;
                top: 0;
            }
            input {
                -webkit-appearance: none;
            }
            input {
                -webkit-appearance: none;
                border-radius: 0;
            }
        </style>
    </head>
    <body>
        <a class="back" href="<%= request.getParameter("back")%>"><img src="/static/ladylike/images/xs/newsletter_back.png"/></a>
        <div class="cont">
            <div class="logo">
                <img class="logo_img" src="/static/ladylike/images/xs/Ladylike-logo.png"/>
                <div class="row">
                    <div class="col-xs-12">
                        <div id="mc_embed_signup" class="newsletter text-center">
                            <span class="sharp sharpLeft"></span>
                            <span class="sharp sharpRight"></span>
                            <form action="//ladylike.us1.list-manage.com/subscribe/post?u=b89623a4e29393181e6283770&amp;id=5e0319a029" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" class="validate" target="_blank" novalidate>
                                <div id="mc_embed_signup_scroll">
                                    <div class="mc-field-group">                                        
                                        <input type="email" value="" name="EMAIL" class="required email" id="mce-EMAIL" placeholder="Συμπλήρωσε το email σου ..."><br/>
                                        <input type="submit" value="ΑΠΟΣΤΟΛΗ" name="subscribe" id="mc-embedded-subscribe">
                                    </div>
                                    <div id="mce-responses" class="clear">
                                        <div class="response" id="mce-error-response" style="display:none"></div>
                                        <div class="response" id="mce-success-response" style="display:none"></div>
                                    </div>
                                    <div style="position:absolute; left:-5000px;"><input type="text" name="b_b89623a4e29393181e6283770_5e0319a029" tabindex="-1" value=""></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
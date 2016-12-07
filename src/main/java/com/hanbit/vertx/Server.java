package com.hanbit.vertx;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Server extends AbstractVerticle {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Server());
	}
	
	/* Get 방식 네임
	 * 
	 * Address : http://www.naver.com/hello/index.nhn?id=hanbit&no=123
	 * URL : http://www.naver.com/hello/index.nhn
	 * Protocol : http
	 * ㄴhttps (Port : 443) : 보안이 된 프로토콜
	 * Host(컴퓨터이름 : IP주소) : www.naver.com (숫자로 된 컴퓨터 이름 대신 도메인으로 대체)
	 * Port : 80
	 * URI : /hello/index.nhn
	 * QueryString : id=hanbit&no=123
	 * Request Parameter Name : id, no
	 * Request Parameter Value : hanbit, 123
	 */

	@Override
	public void start() throws Exception {
		vertx.createHttpServer().requestHandler(req -> {

			String uri = req.uri();
			String result = "Hello!";

			String[] uris = StringUtils.split(uri, "/");

			if (uris.length == 3 && "plus".equals(uris[0])) {
				int x = Integer.parseInt(uris[1]);
				int y = Integer.parseInt(uris[2]);

				result = "<b>Sum : </b><span style='color:red;'>" + (x + y) + "</span>";
			} else if (uris.length == 2 && "image".equals(uris[0])) {
				String imgeName = uris[1];
				String path = "C:/Users/Public/Pictures/Sample Pictures/" + imgeName + ".jpg";

				req.response().putHeader("content-type", "image/jpeg").sendFile(path);

				return;
			}
			
			else if ("/html".equals(uri)) {
				String[] imgNames = {"Chrysanthemum", "Desert", "Hydrangeas", "Koala"};
				result = "<div>";
				for(String imgName : imgNames) {
					result += "<img src='/image/" + imgName + "' style='width:200px'><br>";	
				}
				result += "</div>";
				
			}

			req.response().putHeader("content-type", "text/html").end(result);

		}).listen(8080);
	}

}

package com.src.api.controller;


import com.src.common.shiro.config.ResponseRestful;
import com.src.common.utils.QINiuFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;

@RestController
@RequestMapping(value="/upload")
public class PublicUploadController {
    public String URL="http://static.yiweibao.cn/";
    @ResponseBody
    @RequestMapping(value = "/uploadFile")
    public ResponseRestful addImage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入了文件上传");
        // 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        // 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        // 获取上传路径
        String realPath = request.getRealPath("/upload/image/");
        //String realPath = saveImageUrl + "/upload/image/";
        String url = "/upload/image/";


        // 设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        // 设置响应给前台内容的PrintWriter对象
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;

        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input
        // type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        if (multipartFile.isEmpty()) {
//            return "{\"error\":\"1\",\"message\":\"请选择文件后上传!\"}";
            return  new ResponseRestful(100,"请选择文件后上传",null);
        } else if (multipartFile.getSize() > 2 * 1024 * 1024) {
//            return "{'error':'1','message':'您上传的文件太大,系统允许最大文件1M!'}";
            return  new ResponseRestful(100,"您上传的文件太大,系统允许最大文件2M!",null);
        } else {
            // 获取文件名
            originalFilename = multipartFile.getOriginalFilename();

            // 文件名后缀处理---start---
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Timestamp(System.currentTimeMillis())) + "_" + new Random().nextInt(5) + suffix;

//			url += newFileName;
            String filePath = "image/"+newFileName;

            try {
                // 判断文件名是否是图片
                if (".jpg".equals(suffix) || ".gif".equals(suffix) || ".png".equals(suffix)) {
                    String imgUlr= QINiuFile.upload(multipartFile,filePath);
                    System.out.println(imgUlr);
                    if(!imgUlr.equals("")){
                        return  new ResponseRestful(200,"上传成功",URL+imgUlr);
                    }else{
                        return new ResponseRestful(100,"上传失败!!!",null);
                    }


                } else {
                    return  new ResponseRestful(100,"文件上传格式错误!!!",null);
                }

            } catch (Exception e) {
                System.out.println("文件[" + newFileName + "]上传失败,堆栈轨迹如下");
                e.printStackTrace();
                return new ResponseRestful(100,"上传失败!!!",null);
            }
        }
    }

}
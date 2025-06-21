package com.controller.admin;

import com.result.Result;
import com.utils.AliOssUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


/**
 * 文件上传控制器
 */
@RestController
@Slf4j
@RequestMapping("/admin/common")
public class UploadController {

    /**
     * 注入阿里云OSS操作器
     */
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传接口
     * 处理文件上传请求，使用阿里云OSS服务进行文件存储
     *
     * @param file 用户上传的文件
     * @return 返回文件上传的结果，包括文件的访问URL
     * @throws Exception 文件上传过程中可能抛出的异常
     */

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀  dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf( "."));
            //构造新文件名称
            String objectName
                    = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {

            log.error("文件上传失败：");
        }
            return null;
    }

    /*@PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws Exception {
       // 记录文件上传日志
        log.info("文件上传：{}", file.getOriginalFilename());
        // 检查文件是否为空
        if (!file.isEmpty()) {
            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 调用阿里云OSS操作器上传文件，并获取文件的访问URL
            String url = aliOssUtil.upload(file.getBytes(), AliOssUtil.generateUniqueName(extName));
            // 返回文件上传成功的结果，包含文件的访问URL
            return Result.success(url);
        }
        // 如果文件为空，返回上传失败的结果
        return Result.error("上传失败");
    }*/

    /*@Value("${takeout.file.upload-dir}")
    private String uploadDir;
    @Value("${takeout.file.download-dir}")
    private String downloadDir;

    *//**
     * 文件上传
     *//*
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String newName= UUID.randomUUID()+"_"+originalFilename;
        Path path= Paths.get(uploadDir).resolve(newName);
        try {
            Files.createDirectories(Paths.get(uploadDir));
            file.transferTo(path.toFile());
            return Result.success(downloadDir+"?name="+newName);
        } catch (IOException e) {
            return Result.error("文件上传失败");
        }

    }

    *//**
     * 文件下载
     *//*
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        //输入流,通过输入流读取文件内容
        try(FileInputStream input=new FileInputStream(uploadDir+"//"+name)){

            //输出流，通过输出流将文件导出
            try(ServletOutputStream output=response.getOutputStream()){

                response.setContentType("image/jpeg");
                int len=0;
                byte[] bytes=new byte[1024];
                while ((len=input.read(bytes))!=-1){
                    output.write(bytes,0,len);
                    output.flush();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }*/
}
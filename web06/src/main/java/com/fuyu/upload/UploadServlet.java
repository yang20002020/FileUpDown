package com.fuyu.upload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/*
文件上传的servlet
 */
@WebServlet(urlPatterns = "/UploadServlet")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            //1.创建磁盘文件项工厂
            DiskFileItemFactory diskFileItemFactory=new DiskFileItemFactory();
            //2.创建一个核心的解析类
            ServletFileUpload fileUpload=new ServletFileUpload(diskFileItemFactory);
            //3.利用核心类解析request,解析后会得到多个部分，返回一个list集合。list集合装的是每个部分的内容(FileItem文件项)
            List<FileItem> list=fileUpload.parseRequest( req);
            //4.遍历List 集合，会得到代码每隔部分的文件项的对象。根据文件项判断是否是文件上传项。
            for(FileItem fileItem :list){
                   //判断这个文件项是否是普通项目还是文件上传项
                   if(fileItem.isFormField()){
                       //普通项
                       //接收普通项目的值：(接收值不能再使用request.getParameter())
                       //获得普通项的名称
                       String name=fileItem.getFieldName();
                       //获得普通项的值
                       String value=fileItem.getString("UTF-8");
                       System.out.println(name+"  "+value);
                   }else{
                       //文件上传项：
                       //获得文件上传项的文件的名称:
                       String filename=fileItem.getName();
                       String  subString=StringUtils.substringAfterLast(filename,"\\");
                       //获得文件上传的文件的数据：
                       InputStream is=fileItem.getInputStream();
                       //获得文件上传的路径:磁盘绝对路径
                       String realPath=getServletContext().getRealPath("/upload");
                       //创建一个输出流，写到设备的路径中
                       OutputStream os=new FileOutputStream(realPath+"/"+subString);
                       //两个流对接
                       int len=0;
                       byte[] b=new byte[1024];
                       while((len=is.read(b))!=-1){
                           os.write(b,0,len);
                       }
                       is.close();
                       os.close();
               }

            }
        }catch(Exception e){
              e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req,resp);
    }
}

package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.imgscalr.Scalr;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import domain.PageMaker;
import domain.SearchCriteria;
import service.BmypageDao;
import service.MypageDao;
import service.MypageVo;
import service.OrderDto;
import service.RoomVo;

@WebServlet("/BmypageController")
public class BmypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//���ε� ���� ���
	String uploadPath="D:\\eclipse-workspace\\dajava_a\\webcontent\\";
	//���� ����
	String savedPath="filefolder";
	//����� �� ���
	String saveFullPath = uploadPath + savedPath;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");	
		
		String uri = request.getRequestURI();          
		int len = request.getContextPath().length();   
		String str = uri.substring(len);  
		
		if(str.equals("/bmypage/bmyinfo.do")) {
			
			HttpSession session = request.getSession();  
			int midx = (int) session.getAttribute("midx");
			
			MypageDao md = new MypageDao();                    
			MypageVo mv = md.memberSelectOneInfo(midx);
			
			request.setAttribute("mv",mv);                   
			
			RequestDispatcher rd =request.getRequestDispatcher("/BmyPage/bmyinfo.jsp");     
			rd.forward(request, response);
			
		}else if (str.equals("/bmypage/binfoModify.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/binfoModify.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/binfoModifyAction.do")){
			HttpSession session = request.getSession();  
			int midx = (int) session.getAttribute("midx");
			
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			String addr = request.getParameter("addr");
			
			BmypageDao bmd = new BmypageDao();
										
			bmd.infoModify(id, email, password, phone, addr, midx);
			
			response.sendRedirect(request.getContextPath()+"/bmypage/bmyinfo.do");
		}else if(str.equals("/bmypage/memberlist.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.memberTotal(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<MypageVo> alist = bmd.memberSelectAll(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			request.setAttribute("type", "down");
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/memberlist.jsp");  
			rd.forward(request, response);
			
		}else if (str.equals("/bmypage/myroomdp1.do")) {
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/myroomdp1.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/myroomdp.do")){
			
			String roomnum = request.getParameter("roomnum");
			BmypageDao bmd = new BmypageDao();                    
			RoomVo rv = bmd.roomSelectOne(roomnum);
			
			request.setAttribute("rv",rv);  		
			RequestDispatcher rd =request.getRequestDispatcher("/BmyPage/myroomdp.jsp");    
			rd.forward(request, response);
		}else if (str.equals("/bmypage/roomModify.do")) {
			String roomnum=request.getParameter("roomnum");
			request.setAttribute("roomnum", roomnum);
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/myroomip.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/roomModifyAction.do")){
			int sizeLimit = 1024*1024*15;
			String fileName = null;
			String originFileName = null;
			
			//MultipartRequest ��ü����...filefolder������
			MultipartRequest multi = new MultipartRequest(request, saveFullPath, sizeLimit, "utf-8", new DefaultFileRenamePolicy()); 
			
			//�����ڿ�  ����Name�Ӽ��� �̸��� ��´�
			Enumeration files = multi.getFileNames();
			//��� ���� ��ü�� Name���� ��´�.
			String file = (String)files.nextElement();
			//����Ǵ� �����̸�
			fileName = multi.getFilesystemName(file); 
			//�������� �̸�
			originFileName = multi.getOriginalFileName(file);
			
			String ThumbnailFileName = null;
					
			try {
				if(fileName != null)
				ThumbnailFileName = makeThumbnail(uploadPath,savedPath, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			String rbaseperson = multi.getParameter("rbaseperson");
			String rfullperson = multi.getParameter("rfullperson");
			String rbaseprice = multi.getParameter("rbaseprice");
			String raddperson = multi.getParameter("raddperson");
			String rsupply = multi.getParameter("rsupply");
			String roffseason1 = multi.getParameter("roffseason1");
			String roffseason2 = multi.getParameter("roffseason2");
			String rbusyseason1 = multi.getParameter("rbusyseason1");
			String rbusyseason2 = multi.getParameter("rbusyseason2");
			String roomnum = multi.getParameter("roomnum");
			
			BmypageDao bmd = new BmypageDao();
										
			bmd.roomModify(rbaseperson, rfullperson, rbaseprice, raddperson, rsupply,roffseason1,roffseason2,rbusyseason1,rbusyseason2,fileName, roomnum);
				
			response.sendRedirect(request.getContextPath()+"/bmypage/myroomdp.do?roomnum="+roomnum);
		}else if(str.equals("/bmypage/breserve_canc.do")) {
						
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_canc(keyword);    
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.breserveCanc(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			request.setAttribute("type", "down");  
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_canc.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/cancSort_123.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_canc(keyword);    
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.cancSort_123(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			request.setAttribute("type", "up");
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_canc.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/cancSort_321.do")) {
				
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_canc(keyword);    
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.cancSort_321(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			request.setAttribute("type", "down");
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_canc.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/breserve_pres.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_pres(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.breservePres(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_pres.jsp");
			rd.forward(request, response);
		
		}else if(str.equals("/bmypage/cancelState.do")) {
			
			String oidx = request.getParameter("oidx");
			int oidx2 = Integer.parseInt(oidx);
			
			BmypageDao bmd = new BmypageDao();					
			bmd.cstateModify(oidx2);
			
			response.sendRedirect(request.getContextPath()+"/bmypage/breserve_pres.do");
		}else if(str.equals("/bmypage/breserve_past.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_past(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.breservePast(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_past.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/sort_321.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_past(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.sort_321(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_past.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/sort_123.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.orderTotal_past(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<OrderDto> alist = bmd.sort_123(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/breserve_past.jsp");
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/sort_za.do")) {
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.memberTotal(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<MypageVo> alist = bmd.sort_za(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			request.setAttribute("type", "up");
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/memberlist.jsp");  
			rd.forward(request, response);
			
		}else if(str.equals("/bmypage/sort_az.do")) {
			
			String page= request.getParameter("page");
			if(page==null) page = "1";
			int pagex= Integer.parseInt(page);
			
			String keyword=request.getParameter("keyword");
			if(keyword==null) keyword ="";
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			BmypageDao bmd = new BmypageDao();
			int cnt = bmd.memberTotal(keyword);      
			
			pm.setTotalCount(cnt);			
			
			ArrayList<MypageVo> alist = bmd.sort_az(scri);       
			request.setAttribute("alist",alist);                  
			request.setAttribute("pm", pm);
			request.setAttribute("type", "down");
			
			RequestDispatcher rd = request.getRequestDispatcher("/BmyPage/memberlist.jsp");  
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
	
	private static String makeThumbnail(String uploadPath,String path,String fileName) throws Exception{    //�̹������۰��ҷ���
		
		//�ø� �ҽ������� �о�帰��
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path+File.separator+fileName));
		//�̹����� ������¡�Ѵ�(���� 100�� ���缭 �����̹��� ������ �����Ѵ�)
		BufferedImage destImg = Scalr.resize(sourceImg,Scalr.Method.AUTOMATIC,Scalr.Mode.FIT_TO_HEIGHT,100);
		//����� Ǯ���
		String thumbnailPath = uploadPath + path + File.separator + "s-"+fileName;
		//���� ��ü����
		File newFile = new File(thumbnailPath);
		//Ȯ���� ����
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		//����� �̹��� �����(������¡�� �̹����� �ش� �̹����������� �ش� ��ġ�� ���� ��ü�����Ѵ�)
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		//����� ���� �̸� ����
		return thumbnailPath.substring((uploadPath+path).length()).replace(File.separatorChar, ' ');
	}


}

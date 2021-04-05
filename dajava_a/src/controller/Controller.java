package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.imgscalr.Scalr;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import domain.PageMaker;
import domain.SearchCriteria;
import service.CommentDao;
import service.CommentVo;
import service.NoticeDao;
import service.NoticeVo;
import service.QADao;
import service.QAVo;
import service.ReViewDao;
import service.ReViewVo;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String uploadPath ="D:\\eclipse-workspace\\dajava_a\\WebContent\\";
	String savedPath="filefolder";
	String saveFullPath=uploadPath+savedPath;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		request.setCharacterEncoding("UTF-8");
		//�ѱ� ���� ����
		String uri = request.getRequestURI();
		//��ü ������ 
		int len = request.getContextPath().length();
		//������Ʈ �̸��� ����
		String str = uri.substring(len);
		//������Ʈ �̸��� �� ���� ������ 
		
		if(str.equals("/community/50.do")) {
			
			
			
			String page = request.getParameter("page");
			System.out.println(page);
			
			if (page == null) page="1";
			int pagex  = Integer.parseInt(page);
			String keyword = request.getParameter("keyword");
			if(keyword==null) keyword="";
			
			
			
			System.out.println(pagex);
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			NoticeDao nd = new NoticeDao();
			System.out.println(nd);
			int cnt = nd.Total(keyword);
			System.out.println(cnt);
			pm.setTotalCount(cnt);
			
			
			
			//1. DB�� ����� �����͸� �����´�. 
			//NewBoardDao bv = new NewBoardDao();
			ArrayList<NoticeVo> alist = nd.SelectAll(scri);
			//2.REQUEST ��ü�� ALIST �̸����� ���� ��´�. 
			
			request.setAttribute("alist", alist);                            
			request.setAttribute("pm", pm);
			
			
			
			
			//NoticeDao td = new NoticeDao(); 
			//ArrayList<NoticeVo> alist = td.SelectAll();
			
			//request.setAttribute("alist", alist);
			
			
			
			
			RequestDispatcher rs = request.getRequestDispatcher("/community/50.jsp");
			rs.forward(request, response);
		}
		
		else if(str.equals("/community/51.do")) {
			
			
			
			
			
			RequestDispatcher rs = request.getRequestDispatcher("/community/51.jsp");
			rs.forward(request, response);
			
		}
		else if(str.equals("/community/writeAction.do")) {
			
		
			
			
		//	String Nidx = request.getParameter("Nidx");
			String Ntitle = request.getParameter("Ntitle");
			String Ncontents = request.getParameter("Ncontents");
		//	String Nwriter = request.getParameter("Nwriter");
		//	String Npassword = request.getParameter("Npassword");
		//	String Ndate = request.getParameter("Ndate");
		//	String Ndelyn= request.getParameter("Nndelyn");
		//	int Nidx2 = Integer.parseInt(Nidx);
			
			NoticeDao Nd = new NoticeDao();
			Nd.Insert( Ntitle, Ncontents);
			
			
			System.out.println(Ntitle);
			System.out.println(Ncontents);
		
			
			
			
			response.sendRedirect(request.getContextPath()+"/community/50.do");
		}
		else if (str.equals("/community/51-1.do")) {
			
			String Nidx = request.getParameter("Nidx");
			int Nidx2 = Integer.parseInt(Nidx);
			
			NoticeDao nd = new NoticeDao();
			NoticeVo nv = nd.SelectOne(Nidx2);
			
			request.setAttribute("nv", nv);
			
			
			RequestDispatcher rs = request.getRequestDispatcher("/community/51-1.jsp");
			rs.forward(request, response);	
			
		}
	
		else if(str.equals("/community/51-2.do")) {
			
			
			
			String Nidx = request.getParameter("Nidx");
			int Nidx2 = Integer.parseInt(Nidx);
			NoticeDao nd = new NoticeDao();
			NoticeVo nv = nd.SelectOne(Nidx2);
			
			request.setAttribute("nv", nv);
			


			RequestDispatcher rs = request.getRequestDispatcher("/community/51-2.jsp");
			rs.forward(request, response);	
		}
		else if(str.equals("/community/changeAction.do")) {
			
			
			String Ntitle = request.getParameter("Ntitle");
			String Ncontents = request.getParameter("Ncontents");
			String Nidx = request.getParameter("Nidx");
			int Nidx2 = Integer.parseInt(Nidx);
		
			
			NoticeDao nd = new NoticeDao(); 
			nd.Update(Ntitle,Ncontents,Nidx2);
			
			
			response.sendRedirect(request.getContextPath()+"/community/50.do");
			
		}
		
		else if(str.equals("/community/51-3.do")) {
			
			String Nidx = request.getParameter("Nidx");
			int Nidx2 = Integer.parseInt(Nidx);
			request.setAttribute("Nidx2", Nidx2);
			
			
			
			RequestDispatcher rs =request.getRequestDispatcher("/community/51-3.jsp");
			rs.forward(request,response);
		}else if(str.equals("/community/deleteAction.do")) {
			String Nidx =request.getParameter("Nidx");
			String Npassword= request.getParameter("Npassword");
			int Nidx2 = Integer.parseInt(Nidx);
			NoticeDao td = new NoticeDao();
			td.deleteOne(Nidx2, Npassword);
			
			
			PrintWriter out = response.getWriter();
			
			   String sto="";
			   sto = "<script language='javascript'>";
			   sto += "opener.window.location.reload();";  //������ ���ΰ�ħ
			   sto += "self.close();";   // â�ݱ�
			   sto += "opener.location.href='/dajava_a/community/50.do'";
			   sto += "</script>";
			   out.print(sto);
		
		}
		
		// ���⼭���� qa 
		
		else if(str.equals("/community/54.do")) {
			
			
			String page = request.getParameter("page");
			
			
			if (page == null) page="1";
			int pagex  = Integer.parseInt(page);
			String keyword = request.getParameter("keyword");
			if(keyword==null) keyword="";
			
			
			
			
			
			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setKeyword(keyword);
			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			
			QADao qd = new QADao();
			
			int cnt = qd.Total(keyword);
		
			pm.setTotalCount(cnt);
			
			
			
			//1. DB�� ����� �����͸� �����´�. 
			//NewBoardDao bv = new NewBoardDao();
			ArrayList<QAVo> alist = qd.SelectAll(scri);
			//2.REQUEST ��ü�� ALIST �̸����� ���� ��´�. 
			
			request.setAttribute("alist", alist);                            
			request.setAttribute("pm", pm);
			
			
			
			
			//NoticeDao td = new NoticeDao(); 
			//ArrayList<NoticeVo> alist = td.SelectAll();
			
			//request.setAttribute("alist", alist);
			
			
			
			
			RequestDispatcher rs = request.getRequestDispatcher("/community/54.jsp");
			rs.forward(request, response);
			
			
		}
			else if(str.equals("/community/55.do")) {
				
				
				
				
				
				RequestDispatcher rs = request.getRequestDispatcher("/community/55.jsp");
				rs.forward(request, response);
				
			}
			
				else if(str.equals("/community/writeAction1.do")) {
					
					
					
					
			
						String Qtitle = request.getParameter("Qtitle");
						String Qcontents = request.getParameter("Qcontents");
						String Ridx =request.getParameter("Ridx");
						String Midx =request.getParameter("Midx");
						String QPassword =request.getParameter("QPassword");
						
						QADao Qd = new QADao();
						Qd.Insert( Qtitle, Qcontents,Ridx,Midx,QPassword);
						
						
					
					
						
						
						
						response.sendRedirect(request.getContextPath()+"/community/54.do");
					}
				else if (str.equals("/community/54-1.do")) {
					
					String Qidx = (String)request.getParameter("Qidx");
					int Qidx2 = Integer.parseInt(Qidx);
					
					QADao qd = new QADao();
					QAVo qv = qd.SelectOne(Qidx2);
					
					request.setAttribute("qv", qv);
					request.getAttribute("QoriginQidx:"+qv.getQORIGINQIDX());
					
					RequestDispatcher rs = request.getRequestDispatcher("/community/54-1.jsp");
					rs.forward(request, response);	
					
					
					
				}
				else if (str.equals("/community/54-2.do")) {
					
					String Qidx = request.getParameter("Qidx");
					
					int Qidx2 = Integer.parseInt(Qidx);
					
					QADao qd = new QADao();
					QAVo qv = qd.SelectOne(Qidx2);
					
					request.setAttribute("qv", qv);
					
					
					RequestDispatcher rs = request.getRequestDispatcher("/community/54-2.jsp");
					rs.forward(request, response);	
					
				}
				else if(str.equals("/community/changeAction1.do")) {
					
					
					String Qtitle = request.getParameter("Qtitle");
					String Qcontents = request.getParameter("Qcontents");
					String Qidx = request.getParameter("Qidx");
					String Ridx = request.getParameter("Ridx");
					int Qidx2 = Integer.parseInt(Qidx);
				
					
					QADao qd = new QADao(); 
					int value = qd.Update(Qtitle,Qcontents,Ridx,Qidx2);
					
					
					response.sendRedirect(request.getContextPath()+"/community/54.do");
					
				}
				else if(str.equals("/community/54-3.do")) {
					
					String Qidx = request.getParameter("Qidx");
					int Qidx2 = Integer.parseInt(Qidx);
					request.setAttribute("Qidx2", Qidx2);
					
					
					
					RequestDispatcher rs =request.getRequestDispatcher("/community/54-3.jsp");
					rs.forward(request,response);
				}else if(str.equals("/community/deleteAction1.do")) {
					String Qidx =request.getParameter("Qidx");
					String QPassword =request.getParameter("QPassword");
					
					int Qidx2 = Integer.parseInt(Qidx);
					QADao qd = new QADao();
					qd.deleteOne(Qidx2,QPassword);
					
					
					response.sendRedirect(request.getContextPath()+"/community/54.do");
				
				}
				else if(str.equals("/community/Confirm.do")) 
				
				{
					
					String Qidx = (String)request.getParameter("Qidx");
					int Qidx2 = Integer.parseInt(Qidx);
					
					QADao qd = new QADao();
					QAVo qv = qd.SelectOne(Qidx2);
					
					request.setAttribute("qv", qv);

					RequestDispatcher rs = request.getRequestDispatcher("/community/Confirm.jsp");
					rs.forward(request, response);
					
					
				}
			
				else if(str.equals("/community/54-4.do")) {
					
					
					
					String Qidx =request.getParameter("Qidx");
					String QoriginQidx = request.getParameter("QoriginQidx");
					String Qdepth = request.getParameter("Qdepth");
					String Qlevel = request.getParameter("Qlevel");
					
					String Ridx  = request.getParameter("Ridx");
					request.setAttribute("Qidx", Qidx);
					request.setAttribute("QoriginQidx", QoriginQidx);
					request.setAttribute("Qdepth", Qdepth);
					request.setAttribute("Qlevel", Qlevel);
					
					request.setAttribute("Ridx", Ridx);
					System.out.println(Qidx);
					System.out.println(QoriginQidx);
					System.out.println(Qdepth);
					System.out.println(Qlevel);
					
					System.out.println(Ridx);
					System.out.println("00000");
					
					RequestDispatcher rs =request.getRequestDispatcher("/community/54-4.jsp");
					rs.forward(request,response);
					
					
					
				}
				else if(str.equals("/community/reAction1.do")) {
					
					String Qidx =request.getParameter("Qidx");
					
					String QoriginQidx = request.getParameter("QoriginQidx");
					int QoriginQidx2 = Integer.parseInt(QoriginQidx);
					String Qdepth = request.getParameter("Qdepth");
					int Qdepth2 = Integer.parseInt(Qdepth);
					String Qlevel = request.getParameter("Qlevel");
					int Qlevel2 = Integer.parseInt(Qlevel);
					String Qtitle = request.getParameter("Qtitle");
					String Qcontents= request.getParameter("Qcontents");
					
					String Qpassword = request.getParameter("Qpassword");	
					String Midx =request.getParameter("Midx");
					
					String Ridx =request.getParameter("Ridx");
					System.out.println(Qidx);
					System.out.println(QoriginQidx);
					System.out.println(Qdepth);
					System.out.println(Qlevel);
					System.out.println(Midx);
					System.out.println(Ridx);
					System.out.println(Qtitle);
					System.out.println(Qcontents);
					System.out.println(Qpassword);
				
					QADao qd = new QADao();
					qd.QaRE(QoriginQidx2,Qlevel2, Qdepth2, Qtitle, Qcontents, Qpassword,Midx, Ridx);
				
					response.sendRedirect(request.getContextPath()+"/community/54.do");
				
				}
				
		
				//���⼭���� ���� 
		
				else if(str.equals("/community/52.do")) {
					
					
					String page = request.getParameter("page");
					System.out.println(page);
					
					if (page == null) page="1";
					int pagex  = Integer.parseInt(page);
					String keyword = request.getParameter("keyword");
					if(keyword==null) keyword="";
					
					
					
					System.out.println(pagex);
					
					SearchCriteria scri = new SearchCriteria();
					scri.setPage(pagex);
					scri.setKeyword(keyword);
					
					PageMaker pm = new PageMaker();
					pm.setScri(scri);
					
					ReViewDao nd = new ReViewDao();
					System.out.println(nd);
					int cnt = nd.Total(keyword);
					System.out.println(cnt);
					pm.setTotalCount(cnt);
					
					
					
					//1. DB�� ����� �����͸� �����´�. 
					//NewBoardDao bv = new NewBoardDao();
					ArrayList<ReViewVo> alist2 = nd.SelectAll(scri);
					//2.REQUEST ��ü�� ALIST �̸����� ���� ��´�. 
					
					request.setAttribute("alist2", alist2);                            
					request.setAttribute("pm", pm);
					
					
					
					
					//NoticeDao td = new NoticeDao(); 
					//ArrayList<NoticeVo> alist = td.SelectAll();
					
					//request.setAttribute("alist", alist);
					
					
					
					
					RequestDispatcher rs = request.getRequestDispatcher("/community/52.jsp");
					rs.forward(request, response);
					
					
				}
					else if(str.equals("/community/53.do")) {
					
					
					RequestDispatcher rs = request.getRequestDispatcher("/community/53.jsp");
					rs.forward(request, response);
					
				}
				
					else if(str.equals("/community/writeAction2.do")) {
						
			/*
			 * int sizeLimit = 1024*1024*15; String ReFile = null; String originFileName =
			 * null;
			 * 
			 * //MultipartRequest ��ü���� MultipartRequest multi = new
			 * MultipartRequest(request, saveFullPath, sizeLimit, "utf-8", new
			 * DefaultFileRenamePolicy());
			 * 
			 * //�����ڿ� ����Name�Ӽ��� �̸��� ��´� Enumeration files = multi.getFileNames(); //��� ����
			 * ��ü�� Name���� ��´�. String file = (String)files.nextElement(); //����Ǵ� �����̸� ReFile
			 * = multi.getFilesystemName(file); //�������� �̸� originFileName =
			 * multi.getOriginalFileName(file);
			 * 
			 * String ThumbnailFileName = null;
			 * 
			 * try { if(ReFile != null) ThumbnailFileName = makeThumbnail(uploadPath,
			 * savedPath, ReFile); } catch (Exception e) { e.printStackTrace(); }
			 */
							
							
							//2. Dao��ü�� �������Ѽ� �Ѿ��  ���� �Է��Ѵ�
						
							String Retitle = request.getParameter("Retitle");
							String Recontents = request.getParameter("Recontents");
							String Ridx =request.getParameter("Ridx");
							String Midx =request.getParameter("Midx");
							String RePassword =request.getParameter("RePassword");
							
							System.out.println("test:::::::::"+Ridx);
							ReViewDao rd = new ReViewDao();
							rd.Insert( Retitle, Recontents,Ridx,Midx,RePassword);
							
							
						
						
							
							
							
							response.sendRedirect(request.getContextPath()+"/community/52.do");
						}
					else if (str.equals("/community/52-1.do")) {
						
						String page = request.getParameter("page");
						System.out.println(page);
						
						if (page == null) page="1";
						int pagex  = Integer.parseInt(page);
						String keyword = request.getParameter("keyword");
						if(keyword==null) keyword="";
						
						

						
						System.out.println(pagex);
						
						SearchCriteria scri = new SearchCriteria();
						scri.setPage(pagex);
						scri.setKeyword(keyword);
						
						PageMaker pm = new PageMaker();
						pm.setScri(scri);
						
						ReViewDao nd = new ReViewDao();
						System.out.println(nd);
						int cnt = nd.Total(keyword);
						System.out.println(cnt);
						pm.setTotalCount(cnt);
						
						
						
						//1. DB�� ����� �����͸� �����´�. 
						//NewBoardDao bv = new NewBoardDao();
						ArrayList<ReViewVo> alist2 = nd.SelectAll(scri);
						//2.REQUEST ��ü�� ALIST �̸����� ���� ��´�. 
						
						request.setAttribute("alist2", alist2);                            
						request.setAttribute("pm", pm);
						
						
						
						
						ReViewDao no = new ReViewDao();
						ArrayList<ReViewVo> al = no.SelectTop();
												
						request.setAttribute("al", al);       

						
						
						
						
						
						String Reidx = (String)request.getParameter("Reidx");
						int Reidx3 = Integer.parseInt(Reidx);
						

						
					
						
					
						
						
						PageMaker pm2 = new PageMaker();
						pm2.setScri(scri);
						CommentDao cd = new CommentDao();
						System.out.println(cd);
						int cnt2 = cd.Total(Reidx3);
						System.out.println(cnt2+"����");
						pm2.setTotalCount(cnt2);
						//1. DB�� ����� �����͸� �����´�. 
						
						
						ArrayList<CommentVo> alist = cd.SelectAll(Reidx3,scri);
						//2.REQUEST ��ü�� ALIST �̸����� ���� ��´�. 
			
						request.setAttribute("alist", alist);                            
						request.setAttribute("pm2", pm2);
						
						int Reidx2 = Integer.parseInt(Reidx);
						ReViewDao rd = new ReViewDao();
						
						
						HttpSession session =request.getSession();
						String id = (String)session.getAttribute("id");

						
						int countCheck = 0;
						Cookie[] cookies = request.getCookies(); // ��Ű���� �迭�� �ް� ������ null�� ��ȯ 
						if(cookies !=null) { // ���� ��Ű �迭�� ���� null�� �ƴ϶�� 
							for(int i= 0; i<cookies.length; i++) { // i�� ���� ��Ű �迭�� ���� ��ŭ ���� ��Ű�� 
								if(cookies[i].getName().equals("count"+Reidx) && cookies[i].getValue().equals(id+Reidx)){// ���� ��Ű �迭�� i��° �̸��� count+Reidx�� ���̶�� 
									 countCheck = 0;     			// countcheck ���� 0���� �ʱ�ȭ �ϰ� 
								     break;                        // �׸��� ���� ���´� ���� ���´�.  
								
								
							}else{
							     Cookie cookie = new Cookie("count"+Reidx,id+Reidx); // cookie ���� null�̶�� ��Ű ������ �Ѵ�. 
							     //�̸��� count +Reidx 
							     //��� ���� reidx �̶�� ? �ϳ��� �����ǵ� �� ���� ���� �Ǳ⿡ ��ȸ���� 1�� �ö��� �̻��� 
							     cookie.setMaxAge(60*60*24); //��Ű�� ���� �ð� 
							     cookie.setPath("/"); // ��Ű�� ����� / ���� 
							     response.addCookie(cookie); // ����� ��Ű���� ���Ѵ�. 
							     countCheck += 1; /// �׸��� ī��Ʈ üũ���� 1�� ���Ѵ�. 
							     
							    }
							   }
							
							
						}
						if(countCheck > 0) { // �׸��� ī��Ʈ üũ ���� 0���� ũ�ٸ� �Ʒ��� �޼ҵ带 ���� ��Ű��  0�� ���ų� ������ �����Ű�� �ʰ� �Ʒ��� ����. 
							
							int a = rd.Rview(Reidx2);
							
						}
						
						
						ReViewVo rv = rd.SelectOne(Reidx2);
						
						request.setAttribute("rv", rv);
						
						
						RequestDispatcher rs = request.getRequestDispatcher("/community/52-1.jsp");
						rs.forward(request, response);	
						
					}
					else if (str.equals("/community/52-2.do")) {
						
						String Reidx = request.getParameter("Reidx");
						
						int Reidx2 = Integer.parseInt(Reidx);
						
						ReViewDao rd = new ReViewDao();
						ReViewVo rv = rd.SelectOne(Reidx2);
						
						request.setAttribute("rv", rv);
						
						
						RequestDispatcher rs = request.getRequestDispatcher("/community/52-2.jsp");
						rs.forward(request, response);	
						
					}
					else if(str.equals("/community/changeAction2.do")) {
						
						
						String Retitle = request.getParameter("Retitle");
						String Recontents = request.getParameter("Recontents");
						String Reidx = request.getParameter("Reidx");
						String Ridx = request.getParameter("Ridx");
						int Reidx2 = Integer.parseInt(Reidx);
						int Ridx2 = Integer.parseInt(Ridx);
						System.out.println("reidx : "+Reidx);
						
						ReViewDao rd = new ReViewDao(); 
						rd.Update(Retitle,Recontents,Ridx2,Reidx2);
						
						
						response.sendRedirect(request.getContextPath()+"/community/52.do");
						
					}
					else if(str.equals("/community/52-3.do")) {
						
						String Reidx = request.getParameter("Reidx");
						int Reidx2 = Integer.parseInt(Reidx);
						request.setAttribute("Reidx2", Reidx2);
						
						
						
						RequestDispatcher rs =request.getRequestDispatcher("/community/52-3.jsp");
						rs.forward(request,response);
					}else if(str.equals("/community/deleteAction2.do")) {
						String Reidx =request.getParameter("Reidx");
						String RePassword =request.getParameter("RePassword");
						
						int Reidx2 = Integer.parseInt(Reidx);
						ReViewDao rd = new ReViewDao();
						rd.deleteOne(Reidx2,RePassword);
						
						
						response.sendRedirect(request.getContextPath()+"/community/52.do");
					
					}
			
					else if(str.equals("/community/CommentAction.do")) {
										
					
					
					String Ccontents = request.getParameter("Ccontents");
					String Reidx =request.getParameter("Reidx");
					String Midx =request.getParameter("Midx");
				
					System.out.println(Ccontents+"Ccontents");
					System.out.println(Reidx+"Reidx");
					System.out.println(Midx+"Midx");
					
					CommentDao cd = new CommentDao();
					cd.Insert(Ccontents,Reidx,Midx);
					
					
					
					
					
					
					
					
					response.sendRedirect(request.getContextPath()+"/community/52-1.do?Reidx="+Reidx);
					
				}else if(str.equals("/community/CommentdeleteAction.do")) {
					String Cidx =request.getParameter("Cidx");
					String Reidx =request.getParameter("Reidx");
					
					int Cidx2 = Integer.parseInt(Cidx);
					int Reidx2 = Integer.parseInt(Reidx);
					System.out.println(Cidx+"Cidx");
					System.out.println(Reidx+"Reidx");
					
					
					CommentDao cd = new CommentDao();
					cd.deleteOne(Cidx2,Reidx2);
					
					
					response.sendRedirect(request.getContextPath()+"/community/52-1.do?Reidx="+Reidx);
						
				}
				
				else if(str.equals("/community/52-4.do")) {
					
					String Cidx = request.getParameter("Cidx");
					String Reidx = request.getParameter("Reidx");
					int Cidx2 = Integer.parseInt(Cidx);
					int Reidx2 = Integer.parseInt(Reidx);
					CommentDao cd = new CommentDao();
					CommentVo cv = cd.SelectOne(Cidx2);
					ReViewDao rd = new ReViewDao();
					ReViewVo rv = rd.SelectOne(Reidx2);
					System.out.println("1"+""+Cidx);
					System.out.println("2"+""+Reidx);
					
					request.setAttribute("cv", cv);
					request.setAttribute("rv", rv);
					
					
					RequestDispatcher rs =request.getRequestDispatcher("/community/52-4.jsp");
					rs.forward(request,response);
					
				}
				else if(str.equals("/community/CommentChange.do")) {
					
					
				
					String Ccontents = request.getParameter("Ccontents");
					String Cidx =request.getParameter("Cidx");
					int Cidx2 = Integer.parseInt(Cidx);
					String Reidx = request.getParameter("Reidx");
					int Reidx2 = Integer.parseInt(Reidx);
					System.out.println("3"+""+Cidx);
					System.out.println("4"+""+Reidx);
					
					CommentDao cd = new CommentDao(); 
					int value = cd.Update(Ccontents,Cidx2,Reidx2);
					
					
					PrintWriter out = response.getWriter();
					
					   String sto="";
					   sto = "<script language='javascript'>";
					   sto += "opener.window.location.reload();";  //������ ���ΰ�ħ
					   sto += "self.close();";   // â�ݱ�
					   sto += "</script>";
					   out.print(sto);

					
				}else if(str.equals("/community/52-5.do")) {
					
					
					
					String Cidx =request.getParameter("Cidx");
					String CoriginCidx = request.getParameter("CoriginCidx");
					String Cdepth = request.getParameter("Cdepth");
					String Clevel = request.getParameter("Clevel");
					
					String Reidx  = request.getParameter("Reidx");
					request.setAttribute("Cidx", Cidx);
					request.setAttribute("CoriginCidx", CoriginCidx);
					request.setAttribute("Cdepth", Cdepth);
					request.setAttribute("Clevel", Clevel);
					request.setAttribute("Reidx", Reidx);
					System.out.println(Cidx);
					System.out.println(CoriginCidx);
					System.out.println(Cdepth);
					System.out.println(Clevel);
					
					System.out.println(Reidx);
					System.out.println("00000");
					
					RequestDispatcher rs =request.getRequestDispatcher("/community/52-5.jsp");
					rs.forward(request,response);
					
					
					
				}

		        else if(str.equals("/community/ReAction.do")) {
					
					String Cidx =request.getParameter("Cidx");
					String CoriginCidx = request.getParameter("CoriginCidx");
					int CoriginCidx2 = Integer.parseInt(CoriginCidx);
					String Cdepth = request.getParameter("Cdepth");
					int Cdepth2 = Integer.parseInt(Cdepth);
					String Clevel = request.getParameter("Clevel");
					int Clevel2 = Integer.parseInt(Clevel);
					String Ccontents= request.getParameter("Ccontents");
					String Midx =request.getParameter("Midx");
					String Reidx  =request.getParameter("Reidx");
					
					System.out.println(Cidx);
					System.out.println(CoriginCidx);
					System.out.println(Cdepth);
					System.out.println(Clevel);
					System.out.println(Midx);
					System.out.println(Reidx);
					System.out.println(Ccontents);
				
					CommentDao cd = new CommentDao();
					cd.CommentRe(CoriginCidx2,Clevel2, Cdepth2,Ccontents,Midx, Reidx);
				
				
				
					PrintWriter out = response.getWriter();
					
					   String sto="";
					   sto = "<script language='javascript'>";
					   sto += "opener.window.location.reload();";  //������ ���ΰ�ħ
					   sto += "self.close();";   // â�ݱ�
					   sto += "</script>";
					   out.print(sto);
					   

					 
					
					
					
					
				
					
					
				}else if (str.equals("/newboard/fileDownload.do")) {
					
					//�Ѿ���� �����̸�
					String fileName = request.getParameter("fileName");			
					//������ ��ġ�� ��ü���	
			 		String filePath = saveFullPath + File.separator + fileName;
			 			 	
			 		byte[] b = new byte[4096]; 
					//�ش���ġ�� �����ϴ� ������ ����Ʈ ��Ʈ������ �о�帰��
					FileInputStream fileInputStream = new FileInputStream(filePath); 
				
					//����(����)Ÿ���� üũ�Ͽ� null�̸� ��Ʈ��Ÿ������ �����Ѵ�
				//	String mimeType = getServletContext().getMimeType(filePath); 
					// ���� ��θ� �����Ѵ�  
					 Path source = Paths.get(filePath);
					 //�� ��ο� �ִ� ������ ����Ÿ���� Ȯ������ ���ϸ� null�� ��ȯ
					  String mimeType = Files.probeContentType(source);			
					//�������� �ٷ�� Ȯ���ڸ�
					if(mimeType == null) { 
							// 8��Ʈ�� �� ����Ÿ�� .iniȮ������ ������ �д´�
							mimeType = "application/octet-stream"; 
						} 
					response.setContentType(mimeType); 
					 			
					
			         // ���ϸ� UTF-8�� ���ڵ� 
			         String sEncoding = new String(fileName.getBytes("UTF-8"),"ISO-8859-1"); 
			         response.setHeader("Content-Disposition", "attachment; fileName= " + sEncoding); 
			          
			         //  �������� ����� �Ҷ� ServletOutputStream �߻�Ŭ����
			         ServletOutputStream servletOutStream = response.getOutputStream(); 
			          
			         int read= 0; 
			         //�о�帰 ������ 4 ����Ʈ Ÿ������ ���Ͽ� ����
			         while((read = fileInputStream.read(b,0,b.length))!= -1){ 
			             servletOutStream.write(b,0,read);             
			         } 
			          
			         servletOutStream.flush(); 
			         servletOutStream.close(); 
			         fileInputStream.close(); 			 	 
						
				} else if(str.equals("/community/Recommendation.do")) {
			    	  
			    	  String Reidx = request.getParameter("Reidx");
			    	  int Reidx2 = Integer.parseInt(Reidx);
			    	  HttpSession session = request.getSession();
			    	  String id = (String)session.getAttribute("id");
					  System.out.println("id"+id);
					  ReViewDao rd = new ReViewDao();
					  
					  
					  
					  int countCheck = 0;
						Cookie[] cookies = request.getCookies(); // ��Ű���� �迭�� �ް� ������ null�� ��ȯ 
						if(cookies !=null) { // ���� ��Ű �迭�� ���� null�� �ƴ϶�� 
							for(int i= 0; i<cookies.length; i++) { // i�� ���� ��Ű �迭�� ���� ��ŭ ���� ��Ű�� 
								if(cookies[i].getName().equals("Recommed"+Reidx) && cookies[i].getValue().equals(id+Reidx)){// ���� ��Ű �迭�� i��° �̸��� count+Reidx�� ���̶�� 
									 countCheck = 0;     			// countcheck ���� 0���� �ʱ�ȭ �ϰ� 
								     break;                        // �׸��� ���� ���´� ���� ���´�.  
								
								
							}else{
							     Cookie cookie = new Cookie("Recommed"+Reidx,id+Reidx); // cookie ���� null�̶�� ��Ű ������ �Ѵ�. 
							     //�̸��� count +Reidx 
							     //��� ���� reidx �̶�� ? �ϳ��� �����ǵ� �� ���� ���� �Ǳ⿡ ��ȸ���� 1�� �ö��� �̻��� 
							     cookie.setMaxAge(60*60*24); //��Ű�� ���� �ð� 60�� *60 =1�ð� *24 =24�ð� �� �Ϸ� ���� ��Ű�� ������ ����  
							     cookie.setPath("/"); // ��Ű�� ����� / ���� 
							     response.addCookie(cookie); // ����� ��Ű���� ���Ѵ�. 
							     countCheck += 1; /// �׸��� ī��Ʈ üũ���� 1�� ���Ѵ�. 
							     
							    }
							   }
							
							
						}
						if(countCheck > 0) { // �׸��� ī��Ʈ üũ ���� 0���� ũ�ٸ� �Ʒ��� �޼ҵ带 ���� ��Ű��  0�� ���ų� ������ �����Ű�� �ʰ� �Ʒ��� ����. 
							
							 rd.Recommend(Reidx2);
							
						}
	                  
					  
			    	  response.sendRedirect(request.getContextPath()+"/community/52-1.do?Reidx="+Reidx);
			    	  
			    	  
			      }
				
			
				
			}

			
			protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
				doGet(request, response);
			}
			
			
				private static String makeThumbnail(String uploadPath,String path,String fileName) throws Exception{
				
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



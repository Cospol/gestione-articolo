package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.ArticoloService;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;

/**
 * Servlet implementation class ExecuteDeleteArticoloServlet
 */
@WebServlet("/ExecuteDeleteArticoloServlet")
public class ExecuteDeleteArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteDeleteArticoloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String idInput = request.getParameter("idInput");
		 ArticoloService articoloService = MyServiceFactory.getArticoloServiceInstance();
		 Articolo articoloInstance= null;
		 try {
			 articoloInstance = articoloService.caricaSingoloElemento(Long.parseLong(idInput)); 
			 if (articoloInstance == null) {
				 request.setAttribute("errorMessage", "Attenzione l' articolo che si vuole eliminare non è presente");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
					return;
			 }
			 articoloService.rimuovi(articoloInstance);
			 request.setAttribute("listaArticoliAttribute", articoloService.listAll());
			 request.setAttribute("successMessage", "Operazione effettuata con successo");
			 request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);
		 } catch (Exception e) {
 			e.printStackTrace();
 			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
 		 }
	}

}

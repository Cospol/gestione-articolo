package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;
import it.gestionearticolijspservletjpamaven.utility.UtilityArticoloForm;

/**
 * Servlet implementation class ExecuteUpdateArticoloServlet
 */
@WebServlet("/ExecuteUpdateArticoloServlet")
public class ExecuteUpdateArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idInputParam = request.getParameter("id");
		String codiceInputParam = request.getParameter("codice");
		String descrizioneInputParam = request.getParameter("descrizione");
		String prezzoInputParam = request.getParameter("prezzo");
		String dataArrivoParam = request.getParameter("dataArrivo");

		Date dataArrivoParsed = UtilityArticoloForm.parseDateArrivoFromString(dataArrivoParam);

		Articolo articoloInstance = new Articolo(Long.parseLong(idInputParam),codiceInputParam, descrizioneInputParam,
				prezzoInputParam != null ? Integer.parseInt(prezzoInputParam) : 0, dataArrivoParsed);
		

		if (!UtilityArticoloForm.validateInput(codiceInputParam, descrizioneInputParam, prezzoInputParam,
				dataArrivoParam) || dataArrivoParsed == null) {
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.setAttribute("modifica_articolo_attr", articoloInstance);
			request.getRequestDispatcher("/articolo/update.jsp").forward(request, response);
			return;
		}

		try {
			MyServiceFactory.getArticoloServiceInstance().aggiorna(articoloInstance);
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore nella modifica dell' articolo");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}


}

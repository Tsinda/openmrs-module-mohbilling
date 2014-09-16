
/**
 * Auto generated file comment
 */
package org.openmrs.module.mohbilling.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.runtime.directive.Parse;
import org.hibernate.SessionFactory;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohbilling.businesslogic.BillPaymentUtil;
import org.openmrs.module.mohbilling.model.BillPayment;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class MohBIllingrReceivedAmountController extends
		ParameterizableViewController {

	protected final Log log = LogFactory.getLog(getClass());
	protected SessionFactory sessionFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//List<String> categories = InsuranceUtil.getAllServiceCategories();
		//List<PatientBill> reportedPatientBills = new ArrayList<PatientBill>();

		ModelAndView mav = new ModelAndView();
		String startDateStr=null;
		String billCollector = null;
		Date startDate = null;
		BillPaymentUtil billPaymentUtil = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		if(request.getParameter("startDate") != null && request.getParameter("billCollector") != null){
			startDateStr = request.getParameter("startDate");
			
			billCollector = request.getParameter("billCollector");
			
			
			if (!startDateStr.equals("")) {
				startDate = (Date) formatter.parse(startDateStr);
			}
			 
			mav.addObject("startDate", startDateStr);
			mav.addObject("billCollector", billCollector);
			 
  
			Integer billCollectorId = Integer.parseInt(billCollector); 
			User collector = Context.getUserService().getUser(billCollectorId);
			
			
		List<BillPayment> billPaymentsByDateAndCollector = billPaymentUtil.getBillPaymentsByDateAndCollector(startDate, collector);
		    
		mav.addObject("billPaymentsByDateAndCollector",billPaymentsByDateAndCollector);
		
		Double TotalReceivedAmount = 0.0 ;
		
		for(BillPayment billPayment  : billPaymentsByDateAndCollector ){
		
			TotalReceivedAmount = TotalReceivedAmount + billPayment.getAmountPaid().doubleValue();	
			
		}
		
		mav.addObject("TotalReceivedAmount",TotalReceivedAmount);	
		}
		/*
		 
		 mav.addObject("allInsurances", InsuranceUtil.getAllInsurances());
			mav.addObject("categories", categories);

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate = null;

		if (request.getParameter("patientId") != null
				&& request.getParameter("insurance") != null
				&& request.getParameter("startDate") != null
				&& request.getParameter("endDate") != null
				&& request.getParameter("serviceId") != null
				&& request.getParameter("billStatus") != null
				&& request.getParameter("billCollector") != null) {

			String patientIdStr = request.getParameter("patientId"), insuranceStr = request
					.getParameter("insurance"), startDateStr = request
					.getParameter("startDate"), endDateStr = request
					.getParameter("endDate"), serviceId = request
					.getParameter("serviceId"), billStatus = request
					.getParameter("billStatus"), billCollector = request
					.getParameter("billCollector");

			Integer insuranceIdInt = null;
			Integer patientId = null;
			Date endDate = null;
			Insurance insurance = null;
			String patientNames = null;
			
			User user = Context.getAuthenticatedUser();
			
			String cashierNames = (user.getPersonName().getFamilyName() != null ? user
					.getPersonName().getFamilyName() : "")
					+ " "
					+ (user.getPersonName().getMiddleName() != null ? user
							.getPersonName().getMiddleName() : "")
					+ " "
					+ (user.getPersonName().getGivenName() != null ? user
							.getPersonName().getGivenName() : "");

			if (!startDateStr.equals("")) {
				startDate = (Date) formatter.parse(startDateStr);
			}

			if (!endDateStr.equals("")) {
				endDate = (Date) formatter.parse(endDateStr);
			}

			if (!request.getParameter("patientId").equals("")) {

				patientIdStr = request.getParameter("patientId");
				patientId = Integer.parseInt(patientIdStr);
				Patient patient = Context.getPatientService().getPatient(patientId);
				patientNames = (patient.getFamilyName() != null ? patient
						.getFamilyName() : "")
						+ " "
						+ (patient.getMiddleName() != null ? patient
								.getMiddleName() : "")
						+ " "
						+ (patient.getGivenName() != null ? patient
								.getGivenName() : "");
			}


			if (!request.getParameter("insurance").equals("")) {
				insuranceIdInt = Integer.parseInt(insuranceStr);
				insurance = InsuranceUtil.getInsurance(insuranceIdInt);
			}

			reportedPatientBills = ReportsUtil.buildCohort(insurance,
					startDate, endDate, patientId, serviceId, billStatus, billCollector);

			mav.addObject("startDateStr", startDateStr);
			mav.addObject("endDateStr", endDateStr);
			mav.addObject("serviceId", serviceId);
			mav.addObject("insurance", insurance);
			mav.addObject("patientId", patientId);
			mav.addObject("billStatus", billStatus);
			mav.addObject("billCollector", billCollector);
			mav.addObject("healthFacilityName",Context.getAdministrationService().getGlobalProperty("billing.healthFacilityName"));
			mav.addObject("address", Context.getAdministrationService().getGlobalProperty("billing.healthFacilityPhysicalAddress"));
			mav.addObject("healthFacilityShortCode",Context.getAdministrationService().getGlobalProperty("billing.healthFacilityShortCode"));
			mav.addObject("healthFacilityLogo", Context.getAdministrationService().getGlobalProperty("billing.healthFacilityLogo"));
			mav.addObject("healthFacilityEmail", Context.getAdministrationService().getGlobalProperty("billing.healthFacilityEmail"));
			mav.addObject("today", new Date());
			mav.addObject("patientNames", patientNames);
			mav.addObject("cashierNames", cashierNames);

			double totalAmount = 0, totalPatientDueAmount = 0, totalInsuranceDueAmount = 0, totalAmountReceived = 0;
			List<Object[]> billObj = new ArrayList<Object[]>();

			for (PatientBill bill : reportedPatientBills) {

				*//** Updating status for unmarked ones *//*
				if(bill.getStatus() == null)
					PatientBillUtil.markBillAsPaid(bill);
				
				Date serviceDate = null;
				double patDueAmt = 0, insDueAmt = 0, totalDueAmt = 0, payments = 0;
				for (PatientServiceBill item : bill.getBillItems()) {
					serviceDate = item.getServiceDate();
					double maxima = item.getService().getMaximaToPay().doubleValue();
					double qty = item.getQuantity();
					double rate = bill.getBeneficiary().getInsurancePolicy()
							.getInsurance().getCurrentRate().getRate();
					double unitPrice = item.getUnitPrice().doubleValue();
					double insuranceDue = (rate	 * (unitPrice * qty) / 100);

					patDueAmt += roundTwoDecimals((maxima * qty) - insuranceDue);

					insDueAmt += roundTwoDecimals(insuranceDue);
					
					totalDueAmt = patDueAmt + insDueAmt;
				}
				//TODO: must check this method called right here from Tag...
				payments = roundTwoDecimals(Long.parseLong(MohBillingTagUtil.getTotalAmountPaidByPatientBill(bill.getPatientBillId())));
				billObj.add(new Object[] {
						Context.getDateFormat().format(serviceDate),
						bill.getBeneficiary().getPolicyIdNumber(),
						bill.getBeneficiary().getPatient().getGivenName() + " " + bill.getBeneficiary().getPatient().getFamilyName(),
						bill.getBillItems(),
						bill.getBeneficiary().getInsurancePolicy().getInsurance().getName(),
						roundTwoDecimals(insDueAmt),
						roundTwoDecimals(patDueAmt),
						roundTwoDecimals(payments),
						roundTwoDecimals(totalDueAmt),
						bill.getStatus()});

				totalAmount += totalDueAmt;
				totalPatientDueAmount += patDueAmt;
				totalInsuranceDueAmount += insDueAmt;
				totalAmountReceived += payments;

			}
			
			mav.addObject("totalAmountReceived",
					roundTwoDecimals(totalAmountReceived));
			mav.addObject("insuranceDueAmount",
					roundTwoDecimals(totalInsuranceDueAmount));
			mav.addObject("patientDueAmount",
					roundTwoDecimals(totalPatientDueAmount));
			mav.addObject("totalAmount", roundTwoDecimals(totalAmount));
			mav.addObject("billObj", billObj);
			mav.addObject("reportedPatientBills", reportedPatientBills);
			mav.addObject("startDate", request.getParameter("startDate"));
			mav.addObject("endDate", request.getParameter("endDate"));
			mav.addObject("cashier", Context.getAuthenticatedUser().getPersonName().getGivenName());

			List<String> serviceNames = new ArrayList<String>();
			// new ReportsUtil();
			Map<String, String> billServiceNames = ReportsUtil
					.getAllBillItems(reportedPatientBills);

			for (String key : billServiceNames.keySet()) {
				serviceNames.add(billServiceNames.get(key));
			}

			mav.addObject("serviceNames", serviceNames);

			if (request.getParameter("print") != null)
				if (request.getParameter("print").equals("true")) {

					log.info("00000000000000000000 Reaching there!!");
					if (request.getParameter("reportedPatientBills") != null) {
						// reportedPatientBills =
						// request.getParameter("reportedPatientBills");
						printPatientBillToPDF(request, response,
								reportedPatientBills);
					}

				}

		}
*/
		mav.setViewName(getViewName());

		return mav;

	

	}
	/*private void printPatientBillToPDF(HttpServletRequest request,
			HttpServletResponse response, List<PatientBill> reportedPatientBills)
			throws Exception {
		Document document = new Document();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "Report"); // file name

		PdfWriter writer = PdfWriter.getInstance(document,
				response.getOutputStream());
		writer.setBoxSize("art", new Rectangle(0, 0, 2382, 3369));
		writer.setBoxSize("art", PageSize.A4);

		HeaderFooter event = new HeaderFooter();
		writer.setPageEvent(event);

		document.open();
		Image image1 = Image.getInstance("C:/image1.jpg");
		Image image2 = Image.getInstance("C:/image2.jpg");

		image1.setAbsolutePosition(0, 0);
		image2.setAbsolutePosition(0, 0);

		*//** Adding an image (logo) to the file *//*
		PdfContentByte byte1 = writer.getDirectContent();
		PdfTemplate tp1 = byte1.createTemplate(600, 150);
		tp1.addImage(image2);
		document.setPageSize(PageSize.A4);
		// document.setPageSize(new Rectangle(0, 0, 2382, 3369));

		document.addAuthor(Context.getAuthenticatedUser().getPersonName()
				.toString());// the name of the author

		FontSelector fontTitle = new FontSelector();
		fontTitle.addFont(new Font(FontFamily.COURIER, 10.0f, Font.BOLD));

		// Report title
		Chunk chk = new Chunk("Printed on : "
				+ (new SimpleDateFormat("dd-MMM-yyyy").format(new Date())));
		chk.setFont(new Font(FontFamily.COURIER, 10.0f, Font.BOLD));
		Paragraph todayDate = new Paragraph();
		todayDate.setAlignment(Element.ALIGN_RIGHT);
		todayDate.add(chk);
		document.add(todayDate);

		document.add(fontTitle.process("REPUBLIQUE DU RWANDA\n"));
		try {
			Image image = Image.getInstance("../../images/police.jpg");
			image.setAlignment(Image.ALIGN_LEFT);
			image.setBorder(4900);
			document.add(image);
		} catch (Exception e) {
			log.info("error loading image...... " + e.getMessage());
		}

		document.add(fontTitle.process("POLICE NATIONALE\n"));
		document.add(fontTitle.process("KACYIRU POLICE HOSPITAL\n"));
		document.add(fontTitle.process("B.P. 6183 KIGALI\n"));
		document.add(fontTitle.process("T�l : 584897\n"));
		document.add(fontTitle.process("E-mail : medical@police.gov.rw"));
		// End Report title

		document.add(new Paragraph("\n"));
		chk = new Chunk("Report on patient bills");
		chk.setFont(new Font(FontFamily.COURIER, 10.0f, Font.BOLD));
		chk.setUnderline(0.2f, -2f);
		Paragraph pa = new Paragraph();
		pa.add(chk);
		pa.setAlignment(Element.ALIGN_CENTER);
		document.add(pa);
		document.add(new Paragraph("\n"));

		// title row
		FontSelector fontTitleSelector = new FontSelector();
		fontTitleSelector.addFont(new Font(FontFamily.COURIER, 9, Font.BOLD));

		// Table of identification;
		PdfPTable table = null;
		table = new PdfPTable(2);
		table.setWidthPercentage(100f);

		PdfPCell cell = new PdfPCell(
				fontTitleSelector.process("Compagnie d'Assurance : " + 543));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);

		// tableHeader.addCell(table);

		// document.add(tableHeader);

		document.add(new Paragraph("\n"));

		// Table of bill items;
		float[] colsWidth = { 4f, 4f, 3f, 6f, 5f, 4f, 4f, 3f };
		table = new PdfPTable(colsWidth);
		table.setWidthPercentage(100f);
		BaseColor bckGroundTitle = new BaseColor(170, 170, 170);

		// table Header
		cell = new PdfPCell(fontTitleSelector.process("No"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		// ---------------------------------------------------------------------------
		cell = new PdfPCell(fontTitleSelector.process("Beneficiary"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process("Gender"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process("Policy Id Number"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process("Insurance Name"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process("Insurance due"));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		cell = new PdfPCell(fontTitleSelector.process("Patient due "));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		
		 * cell = new PdfPCell(fontTitleSelector.process("Date "));
		 * cell.setBackgroundColor(bckGroundTitle); table.addCell(cell);
		 

		cell = new PdfPCell(fontTitleSelector.process("Amount "));
		cell.setBackgroundColor(bckGroundTitle);
		table.addCell(cell);

		// normal row
		FontSelector fontselector = new FontSelector();
		fontselector.addFont(new Font(FontFamily.COURIER, 8, Font.NORMAL));

		// empty row
		FontSelector fontTotals = new FontSelector();
		fontTotals.addFont(new Font(FontFamily.COURIER, 9, Font.BOLD));

		// ===========================================================
		int count = 1;
		for (PatientBill pb : reportedPatientBills) {
			
			 * ids += 1;
			 * 
			 * // initialize total amount to be paid on a service
			 * totalToBePaidOnService = 0.0; totalToBePaidOnServiceByInsurance =
			 * 0.0; totalToBePaidOnServiceByPatient = 0.0;
			 * 
			 * cell = new PdfPCell(fontselector.process(ids + "."));
			 * table.addCell(cell);
			 * 
			 * cell = new
			 * PdfPCell(fontselector.process(pb.getBeneficiary().getPatient
			 * ().getNames().toString())); table.addCell(cell);
			 * 
			 * cell = new PdfPCell(fontselector.process("" +
			 * pb.getBeneficiary().getBeneficiaryId()));
			 * //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * table.addCell(cell);
			 * 
			 * cell = new PdfPCell(fontselector.process("" + pb.getAmount()));
			 * //cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * table.addCell(cell);
			 * 
			 * // totalToBePaidOnService = (pb.getQuantity() pb.getUnitPrice()
			 * // .doubleValue());
			 * 
			 * cell = new PdfPCell(fontselector.process("" +
			 * totalToBePaidOnService));
			 * //cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * table.addCell(cell);
			 * 
			 * totalToBePaidOnServiceByInsurance = ((totalToBePaidOnService (pb
			 * .getBeneficiary().getInsurancePolicy().getInsurance()
			 * .getCurrentRate().getRate())) / 100); totalToBePaidByInsurance +=
			 * totalToBePaidOnServiceByInsurance; cell = new
			 * PdfPCell(fontselector.process("" +
			 * totalToBePaidOnServiceByInsurance));
			 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * table.addCell(cell);
			 * 
			 * totalToBePaidOnServiceByPatient = ((totalToBePaidOnService (100 -
			 * pb .getBeneficiary().getInsurancePolicy().getInsurance()
			 * .getCurrentRate().getRate())) / 100); totalToBePaidByPatient +=
			 * totalToBePaidOnServiceByPatient; cell = new
			 * PdfPCell(fontselector.process("" +
			 * totalToBePaidOnServiceByPatient));
			 * cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			 * table.addCell(cell);
			 

			// BaseColor bckGroundTitle = new BaseColor(170, 170, 170);
			// table Header
			cell = new PdfPCell(fontTitleSelector.process("" + count));

			table.addCell(cell);

			// ----------------------------------------------
			cell = new PdfPCell(fontTitleSelector.process(""
					+ pb.getBeneficiary().getPatient().getPersonName()));

			table.addCell(cell);

			cell = new PdfPCell(fontTitleSelector.process(""
					+ pb.getBeneficiary().getPatient().getGender()));

			table.addCell(cell);

			cell = new PdfPCell(fontTitleSelector.process(""
					+ pb.getBeneficiary().getPolicyIdNumber()));

			table.addCell(cell);

			cell = new PdfPCell(fontTitleSelector.process(""
					+ pb.getBeneficiary().getInsurancePolicy().getInsurance()
							.getName()));

			table.addCell(cell);
			Float a = pb.getBeneficiary().getInsurancePolicy().getInsurance()
					.getCurrentRate().getRate();
			BigDecimal b = pb.getAmount();

			Float bFloat = Float.parseFloat(b.toString());
			Float c = a * bFloat;
			cell = new PdfPCell(fontTitleSelector.process("" + c / 100));

			table.addCell(cell);

			cell = new PdfPCell(fontTitleSelector.process(""
					+ (bFloat - (c / 100))));

			table.addCell(cell);

			// for(PatientServiceBill patientServiceBill :pb.getBillItems())

			
			 * cell = new PdfPCell(fontTitleSelector.process(""));
			 * //cell.setBackgroundColor(bckGroundTitle); table.addCell(cell);
			 

			cell = new PdfPCell(fontTitleSelector.process("" + pb.getAmount()));
			// cell.setBackgroundColor(bckGroundTitle);
			table.addCell(cell);

			// normal row
			// FontSelector fontselector = new FontSelector();
			fontselector.addFont(new Font(FontFamily.COURIER, 8, Font.NORMAL));

			// empty row
			// FontSelector fontTotals = new FontSelector();
			fontTotals.addFont(new Font(FontFamily.COURIER, 9, Font.BOLD));
			count++;
		}
		// ================================================================
		table.addCell(cell);

		document.add(table);

		// log.info("reportedPatientBills   new reportedPatientBills reportedPatientBills reportedPatientBills reportedPatientBills  : "+reportedPatientBills.size());
		document.close();

	}
*/
	/*static class HeaderFooter extends PdfPageEventHelper {
		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");

			Phrase header = new Phrase(String.format("- %d -",
					writer.getPageNumber()));
			header.setFont(new Font(FontFamily.COURIER, 4, Font.NORMAL));

			if (document.getPageNumber() > 1) {
				ColumnText.showTextAligned(writer.getDirectContent(),
						Element.ALIGN_CENTER, header,
						(rect.getLeft() + rect.getRight()) / 2,
						rect.getTop() + 40, 0);
			}

			Phrase footer = new Phrase(String.format("- %d -",
					writer.getPageNumber()));
			footer.setFont(new Font(FontFamily.COURIER, 4, Font.NORMAL));

			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_CENTER, footer,
					(rect.getLeft() + rect.getRight()) / 2,
					rect.getBottom() - 40, 0);

		}
	}
*/
	/*static double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}*/
}
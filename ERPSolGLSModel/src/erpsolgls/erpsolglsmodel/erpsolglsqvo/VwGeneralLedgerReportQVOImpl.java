package erpsolgls.erpsolglsmodel.erpsolglsqvo;

import erpsolglob.erpsolglobmodel.erpsolglobclasses.ERPSolGlobClassModel;

import erpsolgls.erpsolglsmodel.erpsolglsqvo.common.VwGeneralLedgerReportQVO;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import oracle.jbo.JboException;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Feb 20 14:55:34 PKT 2022
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class VwGeneralLedgerReportQVOImpl extends ViewObjectImpl implements VwGeneralLedgerReportQVO {
    /**
     * This is the default constructor (do not remove).
     */
    public VwGeneralLedgerReportQVOImpl() {
    }
    
    
    public void doSetERPSolGLReportDefault() {
        ViewObject vo=getApplicationModule().findViewObject("QvoGLLoc");
        if (vo!=null) {
            vo.remove();
       }
        vo=getApplicationModule().createViewObjectFromQueryStmt("QvoGLLoc", "select locationid, location_description from all_locations where locationid='"+ERPSolGlobClassModel.doGetUserLocationCode()+"'");
        vo.executeQuery();;
        this.first().setAttribute("txtLocationId", vo.first().getAttribute(0));
        this.first().setAttribute("txtLocationName", vo.first().getAttribute(1));
        System.out.println(ERPSolGlobClassModel.doGetUserCompanyCode() +" comp code");
        this.first().setAttribute("txtCompanyCode", ERPSolGlobClassModel.doGetUserCompanyCode());
    //        this.first().setAttribute("txtFromDate", this.first().getAttribute("txtDefaultDate"));
    //        this.first().setAttribute("txtToDate", this.first().getAttribute("txtDefaultDate"));
        vo.remove();
    }
    
    public void doSetERPSolGLDocumentUnsubmit() {
        CallableStatement cs=this.getDBTransaction().createCallableStatement("begin ?:=pkg_receipt.func_unsubmit_gl_voucher('"+this.first().getAttribute("txtVoucherseq")+"','"+ERPSolGlobClassModel.doGetUserCode()+"'); END;", 1);
        System.out.println("begin ?:=pkg_receipt.func_unsubmit_gl_voucher('"+this.first().getAttribute("txtVoucherseq")+"','"+ERPSolGlobClassModel.doGetUserCode()+"'); END;");
        try {
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.executeUpdate();
            
    //            if (!cs.getString(1).equals("ERPSOLSUCCESS")) {
                JboException jboex=new JboException(cs.getString(1));
                jboex.setSeverity(JboException.SEVERITY_WARNING); 
                throw new JboException(jboex);
    //           }
    //            this.getDBTransaction().commit();
        } catch (SQLException e) {
        //            this.getCurrentRow().setAttribute("Submit", "N");
            this.getDBTransaction().commit();
            System.out.println(e.getMessage()+ "this is message");
            throw new JboException("Unable to supervise ");
        }
        finally{
            try {
                cs.close();
                System.out.println("closing----");
            } catch (SQLException e) {
            }
        }

        
    }
}


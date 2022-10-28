/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dinnamus.metodosnegocio.venda.caixa;

import MetodosDeNegocio.Crediario.Crediario;
import MetodosDeNegocio.Crediario.entidade.BaixarConta;
import MetodosDeNegocio.Venda.GerenciarCaixa;
import br.String.ManipulacaoString;
import br.TratamentoNulo.TratamentoNulos;
import br.com.FormatarNumeros;
import br.com.generica.Dao_Generica;
import br.com.info.Sistema;
import br.com.log.LogDinnamus;
import br.data.DataHora;
import br.manipulararquivos.ManipulacaoArquivo2;
import br.valor.formatar.FormatarNumero;
import MetodosDeNegocio.Entidades.Dadosorc;
import MetodosDeNegocio.Entidades.Itensorc;
import MetodosDeNegocio.Fachada.clientes;
import MetodosDeNegocio.Venda.PreVenda;
import MetodosDeNegocio.Seguranca.Login;
import MetodosDeNegocio.Seguranca.UsuarioSistema;
import MetodosDeNegocio.Venda.ParcorcRN;
import MetodosDeNegocio.Venda.Venda;
import MetodosDeNegocio.Venda.pdvgerenciar;
import br.arredondar.NumeroArredondar;
import br.com.ManipularString;
import br.data.ManipularData;
import br.data.formatar.DataFormatar;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author dti
 */
public class ComprovanteNaoFiscal {
  
    
    public static String RegistrarItem_Tela_CabecalhoNota_DadosCliente(Long nCodigoCliente){
        return RegistrarItem_Tela_CabecalhoNota_DadosCliente(nCodigoCliente  ,
                true);
    }
    public static String RegistrarItem_Tela_CabecalhoNota_DadosCliente(Long nCodigoCliente , boolean linhaSeparadora)
    {
        String cDadosCliente="";
        ResultSet rsDadosClientes = Dao_Generica.Pesquisar("select  nome,cpf,rg,endereco,bairro,cidade from clientes where codigo=" + nCodigoCliente,Sistema.isOnline());
        try {
            if (rsDadosClientes.next()) {
                if(linhaSeparadora){
                    cDadosCliente += RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                }
                
                cDadosCliente += nCodigoCliente.toString() + " " + rsDadosClientes.getString("nome") + ManipulacaoArquivo2.newline;
                if(rsDadosClientes.getString("cpf")!=null || rsDadosClientes.getString("rg")!=null){
                   cDadosCliente += "CPF     : " + rsDadosClientes.getString("cpf") + "    RG: " + rsDadosClientes.getString("rg")+ ManipulacaoArquivo2.newline;
                }
                if(rsDadosClientes.getString("endereco")!=null){
                    cDadosCliente += "Endereço: " + rsDadosClientes.getString("endereco")+ ManipulacaoArquivo2.newline;
                }
                if(rsDadosClientes.getString("bairro")!=null || rsDadosClientes.getString("cidade")!=null){
                    cDadosCliente += "Bairro  : " + rsDadosClientes.getString("bairro") + "    Cidade: " + rsDadosClientes.getString("cidade")+ ManipulacaoArquivo2.newline;
                }
            }
        } catch (SQLException ex) {
            LogDinnamus.Log(ex);
        }
        return cDadosCliente;
    }

    public static String RegistrarItem_Tela_LinhaSeparadora()
    {
         return RegistrarItem_Tela_LinhaSeparadora("-");
    }
    public static String RegistrarItem_Tela_LinhaSeparadora(String cCaracter)
    {
         return "-----------------------------------------------".replaceAll("-", cCaracter);
    }
     public static String Troca_CabecalhoNota(Long CodigoTroca)
    {
        String cDados="";
   
        cDados+= "COMPROVANTE DE TROCA No :"+ CodigoTroca  +ManipulacaoArquivo2.newline ;
   

        return cDados;
    }
    public static String RegistrarItem_Tela_CabecalhoNota_TipoNota(String cTipoNota)
    {
        String cDados="";
    //    cDados=  "-----------------------------------------" + ManipulacaoArquivo2.newline ;
        if(cTipoNota.equalsIgnoreCase("nfce")){
            cDados+= "CUPOM ELETRÔNICO" +ManipulacaoArquivo2.newline;
        }else{
            cDados+= (cTipoNota.equalsIgnoreCase("CUPOM FISCAL.") || cTipoNota.equalsIgnoreCase("fiscal") ? "CUPOM FISCAL" : "CUPOM VENDA" ) +ManipulacaoArquivo2.newline ;
        }
      //  cDados+= "------- ----------------------------------" + ManipulacaoArquivo2.newline ;
      //  cDados= ManipulacaoString.PadC(cDados, 15) + ManipulacaoArquivo2.newline ;
        return cDados;
    }
    public static String RegistrarItem_Tela_CancelarItens(Itensorc i ,int nSequencia,boolean bRemoverCRLF )
    {
        String cDados="";
        try {
             String cDescricao,  cCodigo;
            Float nPreco,  nQt,  nDesc,  nSubTotal;
            Integer nSeq=nSequencia;

            cDescricao=TratamentoNulos.getTratarString().Tratar(i.getDescricao(),"");
            cCodigo=TratamentoNulos.getTratarString().Tratar(i.getRef(),"");
            nPreco=i.getPreco().floatValue();
            nQt=i.getQuantidade().floatValue();
            nDesc= TratamentoNulos.getTratarBigDecimal().Tratar(i.getDescv(),BigDecimal.ZERO).floatValue();
            nSubTotal=i.getTotal().floatValue();
            cDados= RegistrarItem_Tela_LinhaSeparadora("*") + ManipulacaoArquivo2.newline ;
            cDados+= "ITEM ( "+ nSeq.toString() +" ) CANCELADO"+ ManipulacaoArquivo2.newline;
            cDados+= RegistrarItem_Tela_LinhaSeparadora("*") + ManipulacaoArquivo2.newline ;
            //cDados+= RegistrarItem_Tela_Itens(cDescricao, cCodigo, nPreco, nQt, nDesc, nSubTotal);
            //cDados+= RegistrarItem_Tela_LinhaSeparadora("*") + ManipulacaoArquivo2.newline ;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }

    public static String RegistrarItem_Tela_Itens(String Item ,String cDescricao, String cCodigo, Double nPreco, Double nQt, Double nDesc, Double nSubTotal,String Cor, String Tam ){
            return RegistrarItem_Tela_Itens(Item, cDescricao, cCodigo, nPreco, nQt, nDesc, nSubTotal,false,false,Cor,Tam);
    }
   
    public static String RegistrarItem_Tela_Itens(String Item ,String cDescricao, String cCodigo, Double nPreco, Double nQt, Double nDesc, Double nSubTotal,boolean bRemoverCRLF, boolean  bFracionado, String Cor, String Tam )
    {
        String cDados= "",cPreco="",cQt="",cDesc="",cSubTotal="";

       //cDados+="PRODUTO              QT    PRECO    DESC  SUBTOTAL"+ ManipulacaoArquivo2.newline;
       //         12345678901234567890123456789012345678901234567890

        try {
        //    ManipulacaoString.padLeft(cQt, size, cQt)
        cDados   =  "("+ManipulacaoString.Replicate("0", 3 - Item.trim().length())+ Item +") - "+   cDescricao.substring(0, (cDescricao.trim().length()>=50 ? 50 : cDescricao.trim().length())) ;
        if(!Cor.equalsIgnoreCase("") || !Tam.equalsIgnoreCase("")){
           String DadosCompl =ManipulacaoString.Left(Cor, 30).trim() + " " + ManipulacaoString.Left(Tam, 15).trim() ;
           //if((DadosCompl+cDados).length()<48){
               cDados += " -     " + DadosCompl + ManipulacaoArquivo2.newline;
           //}else{
           //     cDados +=  ManipulacaoArquivo2.newline  + DadosCompl +  ManipulacaoArquivo2.newline;
           ///}
        }else{
           cDados= ManipulacaoString.Left(cDados,48)+ ManipulacaoArquivo2.newline; 
        }
        cCodigo = ManipulacaoString.PrepararStringEmLinha(cCodigo.toString(),13);  //cCodigo.substring(1, 13).trim() + (cCodigo.substring(1, 13).trim().length()<13 ? ManipulacaoString.Replicate(" ", 13-cCodigo.substring(1, 13).trim().length()) : "");


        cQt = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nQt,(bFracionado ?  "##0.000" : "##0.00")),8);
        //cQt = cQt.substring(1, 8).trim() + (cQt.substring(1, 8).trim().length()<8 ? ManipulacaoString.Replicate(" ", 8-cQt.substring(1, 8).trim().length()) : "");//nQt.toString()

        cPreco = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nPreco,"##0.00"),8);
        //cPreco = cPreco.substring(1, 8).trim() + (cPreco.substring(1, 8).trim().length()<8 ? ManipulacaoString.Replicate(" ", 8-cPreco.substring(1, 8).trim().length()) : "");//nQt.toString()

        cDesc = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nDesc,"##0.00"),8);
        
        //cDesc = cDesc.substring(1, 7).trim() + (cDesc.substring(1, 7).trim().length()<7 ? ManipulacaoString.Replicate(" ", 7-cDesc.substring(1, 8).trim().length()) : "");//nQt.toString()

        cSubTotal =ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nSubTotal,"##0.00"),10);
        //cSubTotal = cSubTotal.substring(1, 9).trim() + (cDesc.substring(1, 9).trim().length()<9 ? ManipulacaoString.Replicate(" ", 9-cDesc.substring(1, 8).trim().length()) : "");//nQt.toString()
        
        cDados+=cCodigo+cQt+cPreco+cDesc+cSubTotal +  ManipulacaoArquivo2.newline ;

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

        return cDados;
    }
    public static String RegistrarItem_Imprimir_Itens(String Item ,String cDescricao, String cCodigo, Double nPreco, Double nQt, Float nDesc, Double nSubTotal,boolean bRemoverCRLF, boolean  bFracionado ,String Cor,String Tam)
    {
        String cDados= "",cPreco="",cQt="",cDesc="",cSubTotal="",DadosCompl="";

       //cDados+="PRODUTO              QT    PRECO    DESC  SUBTOTAL"+ ManipulacaoArquivo2.newline;
       //         12345678901234567890123456789012345678901234567890

        try {
        //    ManipulacaoString.padLeft(cQt, size, cQt)
            
        cDados   =  "("+ManipulacaoString.Replicate("0", 3 - Item.trim().length())+ Item +") - "+   
                cDescricao.substring(0, (cDescricao.trim().length()>=50 ? 50 : cDescricao.trim().length())) ;
        
        DadosCompl=(Cor + " " + Tam).trim();
        if(cDados.length()<48){
            cDados =ManipulacaoString.PrepararStringEmLinha_Direita(cDados, 48);        
        }
        if(DadosCompl.length()>0){
            if ((cDados + DadosCompl).trim().length() < 48) {
                cDados += DadosCompl ;
            } else {
                cDados += ManipulacaoArquivo2.newline+ DadosCompl;
            }
        }
        cDados += ManipulacaoArquivo2.newline;
        
        cCodigo = ManipulacaoString.PrepararStringEmLinha(cCodigo.toString(),13);  //cCodigo.substring(1, 13).trim() + (cCodigo.substring(1, 13).trim().length()<13 ? ManipulacaoString.Replicate(" ", 13-cCodigo.substring(1, 13).trim().length()) : "");


        cQt = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nQt,(bFracionado ?  "##0.000" : "##0.00")),8);
        //cQt = cQt.substring(1, 8).trim() + (cQt.substring(1, 8).trim().length()<8 ? ManipulacaoString.Replicate(" ", 8-cQt.substring(1, 8).trim().length()) : "");//nQt.toString()

        cPreco = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nPreco,"##0.00"),8);
        //cPreco = cPreco.substring(1, 8).trim() + (cPreco.substring(1, 8).trim().length()<8 ? ManipulacaoString.Replicate(" ", 8-cPreco.substring(1, 8).trim().length()) : "");//nQt.toString()

        cDesc = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nDesc,"##0.00"),8);
        //cDesc = cDesc.substring(1, 7).trim() + (cDesc.substring(1, 7).trim().length()<7 ? ManipulacaoString.Replicate(" ", 7-cDesc.substring(1, 8).trim().length()) : "");//nQt.toString()

        cSubTotal =ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nSubTotal,"##0.00"),10);
        //cSubTotal = cSubTotal.substring(1, 9).trim() + (cDesc.substring(1, 9).trim().length()<9 ? ManipulacaoString.Replicate(" ", 9-cDesc.substring(1, 8).trim().length()) : "");//nQt.toString()
        cDados+=cCodigo+cQt+cPreco+cDesc+cSubTotal +  ManipulacaoArquivo2.newline ;

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

        return cDados;
    }
    public static String Troca_Dados_Itens(String Item ,String cDescricao, String cCodigo, Float nPreco, Float nQt, Float nDesc, Float nSubTotal,boolean bRemoverCRLF, boolean  bFracionado , String Complemento)
    {
        String cDados= "",cPreco="",cQt="",cDesc="",cSubTotal="";
      
        try {
            cDados   =  "("+ManipulacaoString.Replicate("0", 3 - Item.trim().length())+ Item +") - "+   cDescricao.substring(0, (cDescricao.trim().length()>=50 ? 50 : cDescricao.trim().length())) + ManipulacaoArquivo2.newline;

            cCodigo = ManipulacaoString.PrepararStringEmLinha(cCodigo.toString(),13);  //cCodigo.substring(1, 13).trim() + (cCodigo.substring(1, 13).trim().length()<13 ? ManipulacaoString.Replicate(" ", 13-cCodigo.substring(1, 13).trim().length()) : "");


            cQt = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nQt,(bFracionado ?  "##0.000" : "##0.00")),8);
            //cQt = cQt.substring(1, 8).trim() + (cQt.substring(1, 8).trim().length()<8 ? ManipulacaoString.Replicate(" ", 8-cQt.substring(1, 8).trim().length()) : "");//nQt.toString()

            cPreco = ManipulacaoString.PrepararStringEmLinha(FormatarNumero.FormatarNumero(nPreco,"##0.00"),8);
            //cPreco = cPreco.substring(1, 8).trim() + (cPreco.substring(1, 8).trim().length()<8 ? ManipulacaoString.Replicate(" ", 8-cPreco.substring(1, 8).trim().length()) : "");//nQt.toString()

            cDesc = ManipulacaoString.PrepararStringEmLinha(" ",8);
            //cDesc = cDesc.substring(1, 7).trim() + (cDesc.substring(1, 7).trim().length()<7 ? ManipulacaoString.Replicate(" ", 7-cDesc.substring(1, 8).trim().length()) : "");//nQt.toString()

            cSubTotal =ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nSubTotal,"##0.00"),10);
            //cSubTotal = cSubTotal.substring(1, 9).trim() + (cDesc.substring(1, 9).trim().length()<9 ? ManipulacaoString.Replicate(" ", 9-cDesc.substring(1, 8).trim().length()) : "");//nQt.toString()
            cDados+=cCodigo+cQt+cPreco+cDesc+cSubTotal +  ManipulacaoArquivo2.newline ;
            if(Complemento.length()>0){
               cDados+=Complemento +  ManipulacaoArquivo2.newline;
            }

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }

        return cDados;
    }
    public static String Troca_CabecalhoNota_DescritivoDosItens(boolean bIgnorarCRLF)
    {
        String cDados= "";
        try {
            cDados= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            cDados+="PRODUTO        QT      PRECO            SUBTOTAL"+ ManipulacaoArquivo2.newline;
            cDados+= RegistrarItem_Tela_LinhaSeparadora()+  ManipulacaoArquivo2.newline ;
            //cDados+="1234567890123 9999,999 9999,99  999,99  99999,99"+ ManipulacaoArquivo2.newline;

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }

        return cDados;

    }
    public static String RegistrarItem_Tela_CabecalhoNota_DescritivoDosItens(boolean bIgnorarCRLF)
    {
        String cDados= "";
        try {
            cDados= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            cDados+="PRODUTO        QT      PRECO    DESC   SUBTOTAL"+ ManipulacaoArquivo2.newline;
            cDados+= RegistrarItem_Tela_LinhaSeparadora()+  ManipulacaoArquivo2.newline ;
            //cDados+="1234567890123 9999,999 9999,99  999,99  99999,99"+ ManipulacaoArquivo2.newline;

        } catch (Exception e) {
                LogDinnamus.Log(e);
        }

        return cDados;

    }

    public static String RegistrarItem_Tela_CabecalhoNota_DadosEmpresa(ResultSet rsDadosEmpresa )
    {

        String cDados="";

        try {
            String cNomeEmpresaAtiva = rsDadosEmpresa.getString("nome") == null ? " " : rsDadosEmpresa.getString("nome");
            String cNumero = TratamentoNulos.getTratarString().Tratar(rsDadosEmpresa.getString("numero"),"");
            //String Fantasia = TratamentoNulos.getTratarString().Tratar(rsDadosEmpresa.getString("nomefantasia"),"");
            String cEndeEmpresaAtiva = rsDadosEmpresa.getString("endereco") == null ? " " : rsDadosEmpresa.getString("endereco"); //Form1.LojaAtual.Recordset!Endereco), " ", Form1.LojaAtual.Recordset!Endereco)
            String cFoneEmpresaAtiva = rsDadosEmpresa.getString("fone") == null ? " " : rsDadosEmpresa.getString("fone"); //Form1.LojaAtual.Recordset!Fone), " ", Form1.LojaAtual.Recordset!Fone)
            String cCidadeEmpresaAtiva = rsDadosEmpresa.getString("cidade") == null ? " " : rsDadosEmpresa.getString("cidade"); //Form1.LojaAtual.Recordset!Cidade), " ", Form1.LojaAtual.Recordset!Cidade)
            String cEstadoEmpresaAtiva = rsDadosEmpresa.getString("estado") == null ? " " : rsDadosEmpresa.getString("estado"); //Form1.LojaAtual.Recordset!estado), " ", Form1.LojaAtual.Recordset!estado)
            String cCNPJEmpresaAtiva = rsDadosEmpresa.getString("cnpj") == null ? " " : rsDadosEmpresa.getString("cnpj"); //Form1.LojaAtual.Recordset!CNPJ), " ", Form1.LojaAtual.Recordset!CNPJ)
            String cIEEmpresaAtiva = rsDadosEmpresa.getString("ie") == null ? " " : rsDadosEmpresa.getString("ie"); //Form1.LojaAtual.Recordset!IE), " ", Form1.LojaAtual.Recordset!IE)
            cDados = cNomeEmpresaAtiva + ManipulacaoArquivo2.newline;
            if(cEndeEmpresaAtiva.trim().length()+cFoneEmpresaAtiva.trim().length()>0)
               cDados += cEndeEmpresaAtiva + " " + cNumero +" - Fone: " + cFoneEmpresaAtiva + ManipulacaoArquivo2.newline;

            if(cCidadeEmpresaAtiva.trim().length()+ cEstadoEmpresaAtiva.trim().length()>0)
                cDados += cCidadeEmpresaAtiva + " - " + cEstadoEmpresaAtiva + ManipulacaoArquivo2.newline;
            
            if(cCNPJEmpresaAtiva.trim().length() + cIEEmpresaAtiva.trim().length()>0 )
                cDados += "CNPJ/CPF: " + cCNPJEmpresaAtiva + "  IE: " + cIEEmpresaAtiva + ManipulacaoArquivo2.newline;
        } catch (SQLException ex) {
            LogDinnamus.Log(ex);
        }

        return cDados;
    }
    public static String DadosCliente_Conteudo(Dadosorc d){
        String cDados="";
        try {
            if(!d.getCodcliente().equals("0"))
            {
                cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosCliente(Long.parseLong(d.getCodcliente()));
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String Troca_CabecaNota_Conteudo(ResultSet rsDadosEmpresa, boolean bIgnorarCRLF,boolean ImprimirDadosLojas, Long CodigoTroca)
    {
        boolean bRet=false;
        String cDados="";
        try {
         
            cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;

            cDados+=ComprovanteNaoFiscal.Troca_CabecalhoNota(CodigoTroca);

            //cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;

            if(ImprimirDadosLojas){
                cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosEmpresa(rsDadosEmpresa) + ManipulacaoArquivo2.newline;
            }                    
            cDados+=ComprovanteNaoFiscal.Troca_CabecalhoNota_DescritivoDosItens(bIgnorarCRLF);

        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String CabecaNota_Conteudo(Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean bIgnorarCRLF , ArrayList<Long> Notas )
    {
        boolean bRet=false;
        String cDados="";
        try {            
            cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_TipoNota(cTipoNota);           
            if(rsDadosEmpresa.getBoolean("imprimirdadosdalojanota"))            {
                cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosEmpresa(rsDadosEmpresa) + ManipulacaoArquivo2.newline;
            }
            cDados+= CabecaNota_Conteudo_DadosPDV(d,cTipoNota,true,Notas);           
            cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DescritivoDosItens(bIgnorarCRLF);
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    
    public static String ComprovanteCrediarioExtrato_Cabeca_Conteudo(Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean ForcaExibicao, boolean ExibirDataHora)
    {
        
        String cDados="";
        try {            
            cDados+="EXTRATO DO CREDIARIO" + ManipulacaoArquivo2.newline;
            if(rsDadosEmpresa.getBoolean("imprimirdadosdalojanota"))            {
                cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosEmpresa(rsDadosEmpresa) + ManipulacaoArquivo2.newline;
            }
            //cDados+= ComprovanteCrediario_CabecaNota_Conteudo(d,ExibirDataHora  ,Notas,ForcaExibicao);           
            cDados+= ComprovanteCrediario_CabecaNota_Conteudo(d.getCodigo(), d, ForcaExibicao, ExibirDataHora);           
           // cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DescritivoDosItens(bIgnorarCRLF);
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    
    
    public static String ComprovanteCrediario_Cabeca_Conteudo(Dadosorc d, ResultSet rsDadosEmpresa, String cTipoNota, boolean ForcaExibicao, boolean ExibirDataHora)
    {
        
        String cDados="";
        try {            
            cDados+="RECEBIMENTO" + ManipulacaoArquivo2.newline;
            if(rsDadosEmpresa.getBoolean("imprimirdadosdalojanota"))            {
                cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosEmpresa(rsDadosEmpresa) + ManipulacaoArquivo2.newline;
            }
            //cDados+= ComprovanteCrediario_CabecaNota_Conteudo(d,ExibirDataHora  ,Notas,ForcaExibicao);           
            cDados+= ComprovanteCrediario_CabecaNota_Conteudo(d.getCodigo(), d, ForcaExibicao, ExibirDataHora);           
           // cDados+=ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DescritivoDosItens(bIgnorarCRLF);
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String CabecaNota_Conteudo_DadosPDV(Dadosorc d , String TipoComprovante){
        return CabecaNota_Conteudo_DadosPDV(d, TipoComprovante, true,null,true);
    }
    public static String CabecaNota_Conteudo_DadosPDV(Dadosorc d , String TipoComprovante, boolean ExibirDataHora, ArrayList<Long> PreVendas){
        return CabecaNota_Conteudo_DadosPDV(d, TipoComprovante, ExibirDataHora, PreVendas, true);
    }
    public static String CabecaNota_Conteudo_DadosPDV(Dadosorc d , String TipoComprovante, boolean ExibirDataHora, ArrayList<Long> PreVendas,boolean ForcaExibicao)
    {
        boolean bRet=false;
        String cDados="";
        try {
            if(PreVendas==null){
                PreVendas=new ArrayList<Long>();
            }
            String cNomeCaixa ="";
            try {
                ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(d.getLoja(),0,d.getObjetoCaixa(),0,false);
                
                if ( rsDadosCaixa.next()) {
                    cNomeCaixa=rsDadosCaixa.getString("nome");
                }
            } catch (Exception e) {
                LogDinnamus.Log(e);
            }
            
            if (cNomeCaixa.length()==0) {
                cNomeCaixa =  d.getObjetoCaixa().toString();
            }
            
            boolean ExibirDadosCompl = Sistema.getDadosLoja(Sistema.getLojaAtual(), true).getBoolean("ExibirDadosComplCF");
            if(ExibirDadosCompl || ForcaExibicao){                
                cDados+="ORD : " + d.getCodigo().toString() ;                
                Long CodigoCotacao = TratamentoNulos.getTratarLong().Tratar(d.getCodigocotacao(),0l);
                Long CodigoOrcamento = TratamentoNulos.getTratarLong().Tratar(d.getCodigoorcamento(),0l);
                if(CodigoCotacao>0l){
                   cDados+=" SEQ : " +  CodigoCotacao.toString() + "-" + CodigoOrcamento.toString();
                }
                String LinhasPreVenda ="";                     
                if(PreVendas.size()>0){                  
                    String DadosPreVenda ="";                    
                    for (int i = 0; i < PreVendas.size(); i++) {
                        ResultSet rsDadosVenda = PreVenda.ListarPreVendas(PreVendas.get(i), Sistema.getLojaAtual());

                         if(rsDadosVenda.next()){
                            Long CodigoCotacaoMesclada = rsDadosVenda.getLong("codigocotacao");
                            Integer CodigoOrcamentoMesclada = rsDadosVenda.getInt("codigoorcamento");                    
                            Float Total = rsDadosVenda.getFloat("total");                            
                            DadosPreVenda=  CodigoCotacaoMesclada.toString() + "-" + CodigoOrcamentoMesclada.toString();
                            String NomeVendedor = rsDadosVenda.getString("vendedor");
                            if(PreVendas.size()>1){
                                if(i==0){
                                    LinhasPreVenda+=ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() +  ManipulacaoArquivo2.newline;
                                    LinhasPreVenda+="PRE-VENDA SELECIONADAS" +  ManipulacaoArquivo2.newline;
                                    LinhasPreVenda+=ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() +  ManipulacaoArquivo2.newline;
                                }
                                Integer Tamanho = RegistrarItem_Tela_LinhaSeparadora().length();                                    
                                LinhasPreVenda+= ManipulacaoString.FormataPADR(10,DadosPreVenda , " ") +
                                         "  " + ManipulacaoString.FormataPADR(20,NomeVendedor, " ") +
                                         ManipulacaoString.FormataPADL(Tamanho-30,  "R$ " +FormatarNumero.FormatarNumero(Total,"##0.00") , " ") +  ManipulacaoArquivo2.newline;                                                                
                            }                            
                         }
                    }
                    if(PreVendas.size()>1){
                        LinhasPreVenda+=ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() +  ManipulacaoArquivo2.newline;                        
                    }
                }
                if(LinhasPreVenda.length()>0){
                    cDados+= (PreVendas.size()> 1 ? ManipulacaoArquivo2.newline : " ") + LinhasPreVenda; 
                }else{
                    cDados+= ManipulacaoArquivo2.newline; 
                }
                cDados+="OP  : " + d.getOperador();
                cDados+=" PDV : " + d.getPdv().toString() + " CX : " + cNomeCaixa + ManipulacaoArquivo2.newline;
                if(ExibirDataHora){
                    cDados+="DATA      : " + DataHora.getData( "dd/MM/yyyy",d.getData()) + "  HORA : " + DataHora.getHora( DataHora.FormatHoraPadrao, d.getHora()) + ManipulacaoArquivo2.newline;           
                }
                if(! TratamentoNulos.getTratarString().Tratar(TipoComprovante,"").equalsIgnoreCase("nfiscal")){
                    String codCliente ="",nomeCliente="";
                    codCliente =  TratamentoNulos.getTratarString().Tratar(d.getCodcliente(),"");
                    nomeCliente =  TratamentoNulos.getTratarString().Tratar(d.getCliente(),"");
                    if(!codCliente.equalsIgnoreCase("")){
                         cDados+="CLIENTE : " + codCliente + " - " +nomeCliente + ManipulacaoArquivo2.newline;
                    }
                }
                
            }
            
            
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    
    
    public static String ComprovanteCrediarioExtrato_CabecaNota_Conteudo(Integer Loja , Integer ObjetoCaixa)
    {
       
        String cDados="";
        try {
               
            String cNomeCaixa = "";
            
            ResultSet rsDadosEmpresa = Sistema.getDadosLojaAtualSistema();
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;
            cDados+="EXTRATO DO CREDIARIO" + ManipulacaoArquivo2.newline;
            if(rsDadosEmpresa.getBoolean("imprimirdadosdalojanota"))            {
                cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_CabecalhoNota_DadosEmpresa(rsDadosEmpresa) + ManipulacaoArquivo2.newline;
            }

            ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(Loja, 0, ObjetoCaixa, 0, false);

            if (rsDadosCaixa.next()) {
                cNomeCaixa = rsDadosCaixa.getString("nome");
            }
            cDados += "DATA : " + DataHora.getData("dd/MM/yyyy " + DataHora.FormatHoraPadrao, new Date())  + ManipulacaoArquivo2.newline;
           
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;
            cDados += "CONTA          EMISSAO    VENCTO          TOTAL" + ManipulacaoArquivo2.newline;
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;
           
            
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    
    public static String ComprovanteCrediarioExtrato_Contas_Conteudo(ResultSet DuplicatasAbertas) {
        boolean bRet = false;
        String cDados = "";
        String Linha = "";

        try {

            int TamLinha = ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora().length();

            Double JurosTotal = 0d,ValorDaContas=0d;

            String LinhasContas = "";
            while (DuplicatasAbertas.next()) {
                Long CodigoConta = DuplicatasAbertas.getLong("codigo");

                ResultSet rsDadosConta = Crediario.Duplicata_Consultar(CodigoConta, Sistema.getLojaAtual());
                if (rsDadosConta.next()) {
                    LinhasContas="";
                    //Long CodigoV = rsDadosVenda.getLong("codigocotacao");
                    //Integer CodigoOrcamentoMesclada = rsDadosVenda.getInt("codigoorcamento");                    
                    String Titulo = TratamentoNulos.getTratarString().Tratar(rsDadosConta.getString("titulo"), "");
                    String Parcela = TratamentoNulos.getTratarString().Tratar(rsDadosConta.getString("digito"), "");
                    String Emissao = DataFormatar.Formatar_Data_Em_String_DDMMYYYY(rsDadosConta.getDate("emissao"));
                    String Vencimento = DataFormatar.Formatar_Data_Em_String_DDMMYYYY(rsDadosConta.getDate("dataresto"));
                    Double Juros = TratamentoNulos.getTratarDouble().Tratar(rsDadosConta.getDouble("Valor_juros"), 0d);
                    JurosTotal += Juros;
                    Double ValorOriginal = TratamentoNulos.getTratarDouble().Tratar(rsDadosConta.getDouble("valorrestante"), 0d);
                    Double Total = Juros + ValorOriginal;
                    String Atraso = TratamentoNulos.getTratarString().Tratar(rsDadosConta.getString("atraso"), "");
                    Linha = ManipulacaoString.FormataPADR(14, Titulo + "-" + Parcela, " ") + " "
                            + Emissao + " "
                            + Vencimento + "  "
                            + ManipulacaoString.FormataPADL(9, FormatarNumeros.FormatarParaMoeda(Total), " ") + ManipulacaoArquivo2.newline;
                    LinhasContas += Linha;
                    if (Juros > 0d) {
                        Linha = ">VLR: " + FormatarNumeros.FormatarParaMoeda(ValorOriginal) + " ATR: " + Atraso + " JRS: " + FormatarNumeros.FormatarParaMoeda(Juros) + ManipulacaoArquivo2.newline;
                        if (Linha.trim().length() < TamLinha) {
                            Linha = ManipulacaoString.Replicate(" ", TamLinha - Linha.trim().length()) + Linha;
                        }
                        LinhasContas += Linha;
                    }
                    
                    ValorDaContas+=Total;
                    cDados += LinhasContas;
                }
            }
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;
            Linha = "TOTAL DAS CONTAS : " + FormatarNumeros.FormatarParaMoeda(ValorDaContas) + ManipulacaoArquivo2.newline;
            cDados += ManipulacaoString.Replicate(" ", TamLinha - Linha.trim().length()) + Linha;
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;

        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }

    
    public static String ComprovanteCrediario_CabecaNota_Conteudo(Long  Codigo, Dadosorc d,boolean ForcaExibicao , boolean ExibirDataHora)
    {
       
        String cDados="";
        try {
            
            
            cDados += "ORD      : " + Codigo + ManipulacaoArquivo2.newline;   
            if (ForcaExibicao) {
               
                String cNomeCaixa = "";

                ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixas(d.getLoja(), 0, d.getObjetoCaixa(), 0, false);

                if (rsDadosCaixa.next()) {
                    cNomeCaixa = rsDadosCaixa.getString("nome");
                }


                if (cNomeCaixa.length() == 0) {
                    cNomeCaixa = d.getObjetoCaixa().toString();
                }

                cDados += "OP   : " + d.getOperador();
                cDados += " PDV : " + d.getPdv().toString() + " CX : " + cNomeCaixa + ManipulacaoArquivo2.newline;
            }
            if (ExibirDataHora) {
                cDados += "DATA : " + DataHora.getData("dd/MM/yyyy", d.getData()) + "  HORA : " + DataHora.getHora(DataHora.FormatHoraPadrao, d.getHora()) + ManipulacaoArquivo2.newline;
            }
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;
            cDados += "CONTA          EMISSAO    VENCTO          TOTAL" + ManipulacaoArquivo2.newline;
            cDados += ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline;
           
            
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String ComprovanteCrediario_Contas_Conteudo(Dadosorc d , BaixarConta b)
    {
        boolean bRet=false;
        String cDados="";
        String Linha ="";
        
        ArrayList<Long> Contas = b.getDuplicatas_Codigo();
        Double ValorDaContas = b.getDuplicatas_Divida();
        Double ValorAReceberPelasContas = b.getDuplicatas_A_Receber();
        Double ValorRestante = b.getDuplicatas_ValorRestante();
        
        try {
            if(Contas==null){
                Contas=new ArrayList<Long>();
            }
           
            int TamLinha = ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora().length();
            ///boolean ExibirDadosCompl = Sistema.getDadosLoja(Sistema.getLojaAtual(), true).getBoolean("ExibirDadosComplCF");
            Double JurosTotal =0d;   
           // if(ExibirDadosCompl){                                
                String LinhasContas ="";                     
                if(Contas.size()>0){                  
                                
                    for (int i = 0; i < Contas.size(); i++) {
                         ResultSet rsDadosConta = Crediario.Duplicata_Consultar(Contas.get(i), Sistema.getLojaAtual());
                         if(rsDadosConta.next()){
                            //Long CodigoV = rsDadosVenda.getLong("codigocotacao");
                            //Integer CodigoOrcamentoMesclada = rsDadosVenda.getInt("codigoorcamento");                    
                            String Titulo = TratamentoNulos.getTratarString().Tratar(rsDadosConta.getString("titulo"),"");
                            String Parcela = TratamentoNulos.getTratarString().Tratar(rsDadosConta.getString("digito"),"");
                            String Emissao = DataFormatar.Formatar_Data_Em_String_DDMMYYYY( rsDadosConta.getDate("emissao"));
                            String Vencimento =DataFormatar.Formatar_Data_Em_String_DDMMYYYY(  rsDadosConta.getDate("dataresto"));
                            Double Juros = TratamentoNulos.getTratarDouble().Tratar(rsDadosConta.getDouble("Valor_juros"),0d);                            
                            JurosTotal+=Juros;
                            Double ValorOriginal = TratamentoNulos.getTratarDouble().Tratar( rsDadosConta.getDouble("valorrestante"),0d);                            
                            Double Total = Juros+ValorOriginal;
                            String Atraso = TratamentoNulos.getTratarString().Tratar(rsDadosConta.getString("atraso"),"");                                                                                   
                            Linha =ManipulacaoString.FormataPADR(14,Titulo +"-"+Parcela , " ") + " " +
                                      Emissao  + " " +
                                      Vencimento  + "  " +                                        
                                      ManipulacaoString.FormataPADL(9,FormatarNumeros.FormatarParaMoeda(Total) , " ") +  ManipulacaoArquivo2.newline;                                                                
                            LinhasContas+=Linha;    
                            if(Juros>0d){
                               Linha= ">VLR: " + FormatarNumeros.FormatarParaMoeda(ValorOriginal) + " ATR: "+ Atraso  +" JRS: "+ FormatarNumeros.FormatarParaMoeda(Juros)   +ManipulacaoArquivo2.newline;
                               if(Linha.trim().length()<TamLinha){
                                   Linha = ManipulacaoString.Replicate( " ", TamLinha - Linha.trim().length())  +Linha;
                               }
                               LinhasContas+=Linha ;        
                            }
                         }
                    }
                   
                }
                cDados+= LinhasContas;                 
                 
                cDados+= ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()+ ManipulacaoArquivo2.newline;
                
                JurosTotal = NumeroArredondar.Arredondar_Double(JurosTotal,2);
                
                /*if(b.getDuplicatas_ValorJurosDefinido().doubleValue()!=JurosTotal.doubleValue()){
                    Linha =  "(+)JUROS DEFINIDO: " +FormatarNumeros.FormatarParaMoeda(b.getDuplicatas_ValorJurosDefinido())+ ManipulacaoArquivo2.newline ;
                    cDados+= ManipulacaoString.Replicate( " ", TamLinha - Linha.trim().length())  +Linha ; 
                }*/
                if(b.getDuplicatas_ValorDesconto().doubleValue()>0d){
                    Linha =  "(-)DESCONTO      : " +FormatarNumeros.FormatarParaMoeda(b.getDuplicatas_ValorDesconto())+ ManipulacaoArquivo2.newline ;
                    cDados+= ManipulacaoString.Replicate( " ", TamLinha - Linha.trim().length())  +Linha ; 
                }
                if(ValorRestante>0d){                          
                    Linha =  "TOTAL DAS CONTAS : " +FormatarNumeros.FormatarParaMoeda(ValorDaContas)+ ManipulacaoArquivo2.newline ;
                    cDados+= ManipulacaoString.Replicate( " ", TamLinha - Linha.trim().length())  +Linha ;
                    Linha =  "RECEBIDO PARCIAL : " +FormatarNumeros.FormatarParaMoeda(ValorAReceberPelasContas)+ ManipulacaoArquivo2.newline ;
                    cDados+= ManipulacaoString.Replicate( " ", TamLinha - Linha.trim().length())  +Linha;
                    Linha =  "VALOR RESTANTE   : "  +  FormatarNumeros.FormatarParaMoeda(ValorRestante)+ ManipulacaoArquivo2.newline;
                    cDados+= ManipulacaoString.Replicate( " ", TamLinha - Linha.trim().length())  +Linha;                                        
                    cDados+= ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()+ ManipulacaoArquivo2.newline;
                }
               
               
            //}
            
            
        } catch (Exception e) {

            LogDinnamus.Log(e);
        }
        return cDados;
    }

    public static String SubTotalizaCupom(Dadosorc d){
        String cDados="";
        try {

            cDados= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(d.getDesconto().floatValue()>0f){
                cDados+="VALOR BRUTO   : " + FormatarNumero.FormatarNumero(d.getTotalbruto().floatValue(),"##0.00") + ManipulacaoArquivo2.newline;
                cDados+="DESCONTO      : " + FormatarNumero.FormatarNumero(d.getDesconto().floatValue(),"##0.00") + ManipulacaoArquivo2.newline;
            }
            cDados+="VALOR DA VENDA: " + FormatarNumero.FormatarNumero(d.getValor().floatValue(),"##0.00") + ManipulacaoArquivo2.newline;
            cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String Recebimento_SubTotaliza(Double ValorDuplicatas, int QtDuplicatas,Double ValorRecebidoCliente, Double Desconto, Double Acrescimo){
        String cDados="";
        try {

            //cDados= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            
            if(Desconto>0d || Acrescimo>0d){
                cDados+="VALOR DAS CONTAS : " + FormatarNumeros.FormatarParaMoeda(ValorDuplicatas) + ManipulacaoArquivo2.newline;
            }
            if(Desconto>0d){
                cDados+="DESCONTO         : " + FormatarNumeros.FormatarParaMoeda(Desconto) + ManipulacaoArquivo2.newline;
            }
            if(Acrescimo>0d){
                cDados+="ACRESCIMO        : " + FormatarNumeros.FormatarParaMoeda(Acrescimo) + ManipulacaoArquivo2.newline;
            }

            cDados+="VALOR RECEBIMENTO: " + FormatarNumeros.FormatarParaMoeda(ValorRecebidoCliente) + ManipulacaoArquivo2.newline;
            cDados+="QUANTIDADE       : " + (QtDuplicatas) + ManipulacaoArquivo2.newline;
            cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;

        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
    
    public static String Troca_Fechamento(Double ValorCredito, Double Quantidade, boolean EjetarPapel){
        String cDados="";
        try {
           
            cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            cDados+=ManipulacaoString.PrepararStringEmLinha("VALOR CREDITO :",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(ValorCredito ,"##0.00"),9) + ManipulacaoArquivo2.newline;
            cDados+=ManipulacaoString.PrepararStringEmLinha("QTD DEVOLVIDA :",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(Quantidade ,"000"),9) + ManipulacaoArquivo2.newline;                
          
            cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(EjetarPapel){
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
                cDados+= " " + ManipulacaoArquivo2.newline;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String TerminaFechamento(Float nDinheiro, Float nTroco, String cMensagem, Long nCodigoCliente, Long nCodigoVenda, String CodigoVendedor, String NomeVendedor){
        String cDados="";
        try {
            if(nDinheiro>0f && nTroco>0f){
                cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                cDados+=ManipulacaoString.PrepararStringEmLinha("DINHEIRO",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nDinheiro ,"##0.00"),9) + ManipulacaoArquivo2.newline;
                cDados+=ManipulacaoString.PrepararStringEmLinha("TROCO",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nTroco ,"##0.00"),9) + ManipulacaoArquivo2.newline;                
            }
            //cDados+=RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(nCodigoCliente>0){
                cDados += RegistrarItem_Tela_CabecalhoNota_DadosCliente(nCodigoCliente);
                cDados += DesdobramentoParcelas(nCodigoVenda);
            }
            String codVendedorVinculado = TratamentoNulos.getTratarString().Tratar(UsuarioSistema.getCodVendedorVinculadoAoCaixa(),"");
            
            if(!codVendedorVinculado.equalsIgnoreCase(CodigoVendedor)){
                cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                cDados+=  "VEND : [ "+ CodigoVendedor +" ] " +  NomeVendedor + ManipulacaoArquivo2.newline ;
            }
            
            cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(cMensagem.length()>0){
                cDados+= cMensagem + ManipulacaoArquivo2.newline;
            }else{
                cDados+="DINNAMUS AUTOMACAO" + ManipulacaoArquivo2.newline;
            }            
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
    
    public static String Recebimento_TerminaFechamento(Double nDinheiro, Double nTroco, String cMensagem, Long nCodigoCliente, Long nCodigoVenda){
        String cDados="";
        try {
            if(nDinheiro>0){
                cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                cDados+=ManipulacaoString.PrepararStringEmLinha("DINHEIRO",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nDinheiro ,"##0.00"),9) + ManipulacaoArquivo2.newline;
                if(nTroco>0d){
                    cDados+=ManipulacaoString.PrepararStringEmLinha("TROCO",30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumero.FormatarNumero(nTroco ,"##0.00"),9) + ManipulacaoArquivo2.newline;                
                }
            }
            //cDados+=RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(nCodigoCliente>0){
                cDados += RegistrarItem_Tela_CabecalhoNota_DadosCliente(nCodigoCliente);
                cDados += DesdobramentoParcelas(nCodigoVenda);
            }
            cDados+= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            if(cMensagem.length()>0){
                cDados+= cMensagem + ManipulacaoArquivo2.newline;
            }else{
                cDados+="DINNAMUS AUTOMACAO" + ManipulacaoArquivo2.newline;
            }            
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
            cDados+= " " + ManipulacaoArquivo2.newline;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
    public static String EfetuarFormaPagto(String cConta ,String cNomeForma, Double nValor){
        String cDados="";
        try {
            cDados +=  ManipulacaoString.PrepararStringEmLinha(cConta + " - "+ cNomeForma,30) +  ManipulacaoString.PrepararStringEmLinha( FormatarNumeros.FormatarParaMoeda(nValor ),9) ;
            cDados += ManipulacaoArquivo2.newline;
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }

    public static String DesdobramentoParcelas(Long nCodigoVenda){
        String cDados="";
        String cParcelas="";
        try {
            ResultSet rsDadosParcelas = ParcorcRN.Parcorc_Lista(nCodigoVenda);
            
            while(rsDadosParcelas.next()){
                if(rsDadosParcelas.getString("destino").equalsIgnoreCase("A Receber & Crediario")){
                    cParcelas+=rsDadosParcelas.getString("parcela") + "o Vencto : " + DataHora.getData("dd/MM/yyyy",rsDadosParcelas.getDate("data")) + " - Valor : " + FormatarNumero.FormatarNumeroMoeda( String.valueOf(rsDadosParcelas.getFloat("Valor"))) + ManipulacaoArquivo2.newline;
                }
            }
            if(cParcelas.length()>0){
                cDados+=RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                cDados+="DESDOBRAMENTO DAS PARCELAS" + ManipulacaoArquivo2.newline ;
                cDados+=RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                cDados+=cParcelas;
                cDados+=RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            }
        } catch (Exception e) {
            LogDinnamus.Log(e);
        }
        return cDados;
    }
        public static String ComprovanteFiado(Long nCodigoVenda , Long nCodigoCliente, int nCodigoLoja, Date dDataEmissao){
        String cRet="";
        
        try {
            ResultSet rsDadosCaixa = pdvgerenciar.DadosPdv();
            ResultSet rsDadosCliente = clientes.Listar(nCodigoLoja, nCodigoCliente.toString());
            if(rsDadosCliente.next()){
                cRet =cRet  + "CLIENTE  : "  + rsDadosCliente.getString("NOME") + ManipulacaoArquivo2.newline ;
                cRet =cRet  + "CODIGO   : "  + rsDadosCliente.getString("CODIGO") + ManipulacaoArquivo2.newline ;
                cRet =cRet  + "CPF/CNPJ : "  + rsDadosCliente.getString("CPF") + ManipulacaoArquivo2.newline ;
                cRet =cRet  + "RG/IE    : "  + rsDadosCliente.getString("RG") + ManipulacaoArquivo2.newline ;
                cRet =cRet  + "DOC      : "  + nCodigoVenda + " -  DT.EMISSAO : " + DataHora.getData( DataHora.FormatDataPadrao, dDataEmissao) + ManipulacaoArquivo2.newline ;
                String cNomeCaixa ="";
                ResultSet rsCaixa  =GerenciarCaixa.ListarCaixasPorPDV(pdvgerenciar.CodigoPDV(),nCodigoLoja);
                if(rsCaixa.next()){
                    cNomeCaixa = rsCaixa.getString("nome");
                }
                cRet =cRet  + "OPERADOR : "  + UsuarioSistema.getNomeUsuario() + " -  CAIXA : " + cNomeCaixa  + ManipulacaoArquivo2.newline ;
                cRet =cRet  + ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                cRet =cRet  + "VENCIMENTOS  " + ManipulacaoArquivo2.newline ;
                cRet =cRet  + ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
                ResultSet rsParcelas = ParcorcRN.Parcorc_Lista(nCodigoVenda);
                while(rsParcelas.next()){
                        if(rsParcelas.getString("destino").equalsIgnoreCase("A Receber & Crediario")){
                            cRet =cRet  + "DATA:  "  + DataHora.getData( DataHora.FormatDataPadrao, rsParcelas.getDate("data")) + "  -  VALOR : R$ " + FormatarNumeros.FormatarParaMoeda( rsParcelas.getFloat("VALOR"))   + ManipulacaoArquivo2.newline ;
                        }
                }
               cRet =cRet  + ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
               cRet =cRet  + "RECONHECO QUE PAGAREI A DIVIDA ACIMA" + ManipulacaoArquivo2.newline ;  
               cRet =cRet  + " " + ManipulacaoArquivo2.newline ;  
               cRet =cRet  + " " + ManipulacaoArquivo2.newline ;  
               cRet =cRet  + ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
               cRet =cRet  + rsDadosCliente.getString("NOME")  + ManipulacaoArquivo2.newline ;
               
            }
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        
        return cRet;
    }
   public static String GerarRelatorio(String cControleCX, int nCodigoLoja )
    {
        //GerenciarPorta porta = PDVComprovanteNaoFiscal.getPorta(false);
        try {
            
                      
            ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixasPorPDV( pdvgerenciar.CodigoPDV(), nCodigoLoja, Sistema.isOnline());
            ResultSet rsAbrirCaixa = GerenciarCaixa.AbrirCaixa_Consultar(cControleCX, "A");
            ResultSet rsFecharCaixa = GerenciarCaixa.AbrirCaixa_Consultar(cControleCX, "F");
            
            if(!rsDadosCaixa.next()){
                return "";
            }
            
            if(!rsAbrirCaixa.next()){
                return "";
            }
            
            ResultSet rsDadosOpcx = Login.DadosUsuario(rsDadosCaixa.getInt("operadorcx"), false);
            if(!rsDadosOpcx.next()){
                return "";
            }
            String cTexto = "";
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("DEMONSTRATIVO DE CAIXA") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("CAIXA :" + rsDadosCaixa.getString("nome")) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("DADOS DA ABERTURA") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("  DATA/HORA   :" +  DataHora.getData("dd/MM/yyyy", rsAbrirCaixa.getDate("data"))+  " - "+  DataHora.getData(DataHora.FormatHoraPadrao, rsAbrirCaixa.getTimestamp("hora")) )  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("  OPERADOR    :" +  rsDadosOpcx.getString("nome") )  + ManipulacaoArquivo2.newline ;            
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("ENCERRAMENTO DO DIA")  + ManipulacaoArquivo2.newline ;
            if(rsFecharCaixa.next()){
                cTexto=cTexto+("  DATA/HORA   :" + DataHora.getData("dd/MM/yyyy", rsFecharCaixa.getDate("data"))+  " - "+  DataHora.getData(DataHora.FormatHoraPadrao, rsFecharCaixa.getTimestamp("hora")) )  + ManipulacaoArquivo2.newline ;                
                ResultSet rsDadosOpcxFechou = Login.DadosUsuario(rsFecharCaixa.getInt("codcaixa"), false) ;
                if(!rsDadosOpcxFechou.next()){
                    return "";
                }
                cTexto=cTexto+("  OPERADOR    :"+ rsDadosOpcxFechou.getString("NOME")) + ManipulacaoArquivo2.newline ; 
            }else{
                cTexto=cTexto+("  DATA/HORA   :") + ManipulacaoArquivo2.newline ;
                cTexto=cTexto+("  OPERADOR    :") + ManipulacaoArquivo2.newline ;
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("ABERTURA") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            
            
            rsAbrirCaixa.absolute(0); 
            Float nValorAberturaDinheiro=0f;
            while(rsAbrirCaixa.next()){
                if(rsAbrirCaixa.getString("conta").trim().equalsIgnoreCase("1.1")){
                   nValorAberturaDinheiro=rsAbrirCaixa.getFloat("valor");                    
                }
            }            
            cTexto=cTexto+("   ABERTURA DO CAIXA R$ " + FormatarNumeros.FormatarParaMoeda(nValorAberturaDinheiro))  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("FATURAMENTO")  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            ResultSet rsDadosMovimento  =GerenciarCaixa.Caixa_Saldo(cControleCX);
            String cDescricao="", cConta="",cLinha="",strValor="";
            Float nRecebimento=0f;
            Float nTotalFaturamento =0f;
            Float nValorDinheiro=0f;
            while(rsDadosMovimento.next()){
                 cConta=rsDadosMovimento.getString("conta");
                 cDescricao=rsDadosMovimento.getString("descricao");
                 nRecebimento = rsDadosMovimento.getFloat("valor");
                 if(cConta.equalsIgnoreCase("1.1")){
                     nValorDinheiro += nRecebimento;
                 }
                 cLinha = "   " + cConta + (cConta.length()==0 ? "  " : "");
                 
                 cLinha= ManipulacaoString.FormataPADL(3,cLinha," ") + " " + ManipulacaoString.FormataPADR(20, cDescricao, " ");
                 if(cConta.trim().equalsIgnoreCase("")){
                    nTotalFaturamento  += nRecebimento;
                 }
                 
                 //ManipulacaoString.Replicate(cLinha, WIDTH)
               strValor =  "R$ " + FormatarNumeros.FormatarParaMoeda( nRecebimento);
               cLinha= cLinha + ManipulacaoString.FormataPADL(48 -  cLinha.length(), strValor, " ");
               
               cTexto=cTexto+(cLinha)  + ManipulacaoArquivo2.newline ;
            }
            
            // ENTRADAS DE CAIXA
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("TOTAL DO FATURAMENTO" + ManipulacaoString.FormataPADL(48-"TOTAL DO FATURAMENTO".length() ,"R$ " +  FormatarNumeros.FormatarParaMoeda(nTotalFaturamento), " "))  + ManipulacaoArquivo2.newline ;            
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("(+)REFORÇO DE CAIXA") + ManipulacaoArquivo2.newline ;    
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            ResultSet rsEntradas = GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, "E");
            Float nTotalEntradas =0f;
            while(rsEntradas.next()){
                cLinha = rsEntradas.getInt("cod") + "  " + rsEntradas.getString("conta");
                nTotalEntradas  +=rsEntradas.getFloat("valor");
                strValor = FormatarNumeros.FormatarParaMoeda(rsEntradas.getFloat("valor"));
                cLinha  = cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," ");
                cTexto=cTexto+(cLinha)  + ManipulacaoArquivo2.newline ;
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            if(nTotalEntradas==0f){
                cTexto=cTexto+("****** SEM REGISTRO DE ENTRADAS ******")  + ManipulacaoArquivo2.newline ;
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cLinha="TOTAL DE ENTRADAS";
            strValor = " R$ "+ FormatarNumeros.FormatarParaMoeda(nTotalEntradas);
            cTexto=cTexto+(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," "))  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            
            // SAIDAS DE CAIXA
            cTexto=cTexto+("(-)SAIDAS DE CAIXA")  + ManipulacaoArquivo2.newline ;    
            cTexto=cTexto+(" ")  + ManipulacaoArquivo2.newline ;
            ResultSet rsSaidas = GerenciarCaixa.Caixa_Entradas_E_Saidas(cControleCX, "S");
            Float nTotalSaidas =0f;
            while(rsSaidas.next()){
                cLinha = rsSaidas.getInt("cod") + "  " + rsSaidas.getString("conta");
                nTotalSaidas  +=rsSaidas.getFloat("valor");
                strValor = FormatarNumeros.FormatarParaMoeda(rsSaidas.getFloat("valor"));
                cLinha  = cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," ");
                cTexto=cTexto+(cLinha)  + ManipulacaoArquivo2.newline ;
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            if(nTotalSaidas==0f){
               cTexto=cTexto+("****** SEM REGISTRO DE SAIDAS ******")  + ManipulacaoArquivo2.newline ;
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cLinha="TOTAL DE SAIDAS";
            strValor = " R$ " + FormatarNumeros.FormatarParaMoeda(nTotalSaidas);
            cTexto=cTexto+(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor," "))  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;    
            cTexto=cTexto+("SALDO ATUAL=(DINH.)+(REFORCO)-(SAIDA)") + ManipulacaoArquivo2.newline ;
            Float nSaldoCaixa = nValorDinheiro + nTotalEntradas - nTotalSaidas;
            cTexto=cTexto+("R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa))  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("TOTAL CX.=(DINH.)+(REFORCO)-(SAIDA)+(ABERTURA)")  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("R$ " + FormatarNumeros.FormatarParaMoeda(nSaldoCaixa+nValorAberturaDinheiro))  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("RESUMO POR VENDEDORES") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            ResultSet rsMovimentoVendedor = GerenciarCaixa.Caixa_Saldo_PorVendedor(cControleCX);
            Float nValorTotalVendedor=0f;
            while(rsMovimentoVendedor.next()){
                
                nValorTotalVendedor += rsMovimentoVendedor.getFloat("valor");
                cLinha = ManipulacaoString.FormataPADL(5, String.valueOf(rsMovimentoVendedor.getInt("cod")) , " ") + "  "+  rsMovimentoVendedor.getString("vendedor");
                strValor =  FormatarNumeros.FormatarParaMoeda(rsMovimentoVendedor.getFloat("valor"));
                cTexto=cTexto+(cLinha + ManipulacaoString.FormataPADL(48-cLinha.length(), strValor, " ")) + ManipulacaoArquivo2.newline ;
                        
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("TOTAIS DOS VENDEDORES R$ " + FormatarNumeros.FormatarParaMoeda(nValorTotalVendedor))  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            //PDVComprovanteNaoFiscal.AcionarGuilhotina(strValor)
            //porta.Fechar();
            return cTexto;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
            return "";
        }
    }
   
   public static String MovimentacaoCaixa(Long CodigoMovimentacao,int Loja,int PDV ,String cControleCX)
    {
        //GerenciarPorta porta = PDVComprovanteNaoFiscal.getPorta(false);
        try {
            
                      
            ResultSet rsDadosCaixa = GerenciarCaixa.ListarCaixasPorPDV( PDV, Loja, Sistema.isOnline());
            
            if(!rsDadosCaixa.next()){
                return "";
            }
            ResultSet rsDadosOpcx = Login.DadosUsuario(rsDadosCaixa.getInt("operadorcx"), false);
            if(!rsDadosOpcx.next()){
                return "";
            }
            String cTexto = "";
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("COMPROVANTE DE MOV. DE CAIXA") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+("CAIXA :" + rsDadosCaixa.getString("nome")) + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora()) + ManipulacaoArquivo2.newline ;            
            cTexto=cTexto+(" ")  + ManipulacaoArquivo2.newline ;
            ResultSet rsSaidas = GerenciarCaixa.Caixa_Movimentacao(CodigoMovimentacao);           
            
            Double nTotalSaidas=0d;
            if(rsSaidas.next()){
                cTexto=cTexto+  "CODIGO: " + rsSaidas.getLong("codigo") + ManipulacaoArquivo2.newline;
                cTexto=cTexto+  "CONTA : " + rsSaidas.getString("conta") + "  " + rsSaidas.getString("descricao" ) + ManipulacaoArquivo2.newline;
                String Tipo = rsSaidas.getString("tipo");
                cTexto=cTexto+  "TIPO  : " + (Tipo.startsWith("S") ? "Saida" : "Entrada"    ) + ManipulacaoArquivo2.newline;                                
                nTotalSaidas  =rsSaidas.getDouble("valor");
                cTexto=cTexto+  "VALOR : " +FormatarNumeros.FormatarParaMoeda(nTotalSaidas) + ManipulacaoArquivo2.newline;                                                
                cTexto=cTexto+  "DATA  :" + DataHora.getData(DataHora.FormatDataPadrao, rsSaidas.getDate("data")) + ManipulacaoArquivo2.newline ;                
                cTexto=cTexto+  "HISTORICO  :" + rsSaidas.getString("historico") + ManipulacaoArquivo2.newline ;                
                
                //SaldoCaixa.AddItem Padr(IIf(IsNull(!Codigo), " ", !Codigo), 3, " ") & "  " & cDescricao & Padl(IIf(IsNull(!valor), 0, "R$ " & Format(!valor, "###,##0.00")), 15)
            }
            cTexto=cTexto+(ComprovanteNaoFiscal.RegistrarItem_Tela_LinhaSeparadora())  + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+"IMPRESSO EM   :" +  DataHora.getData("dd/MM/yyyy HH:mm:ss", ManipularData.DataAtual())+ ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            cTexto=cTexto+(" ") + ManipulacaoArquivo2.newline ;
            //PDVComprovanteNaoFiscal.AcionarGuilhotina(strValor)
            //porta.Fechar();
            return cTexto;
        } catch (Exception e) {
            LogDinnamus.Log(e, true);            
            return "";
        }
    }
   
    public static String Mesclagem(ArrayList<Long> PreVendas, Integer Loja){
        String Retorno ="";
        String Dados=""; 
        try {
            Retorno= RegistrarItem_Tela_LinhaSeparadora() + ManipulacaoArquivo2.newline ;
            Retorno+="PRE-VENDA       VENDEDOR                  TOTAL"+ ManipulacaoArquivo2.newline;
            //Retorno+="12345678901234567890123456789012345678901234567"+ ManipulacaoArquivo2.newline;
            Retorno+= RegistrarItem_Tela_LinhaSeparadora()+  ManipulacaoArquivo2.newline ;
            ResultSet DadosPrevenda = null;
            Long CodigoPreVenda = 0l;
            Float Total =0f;
            for (int i = 0; i < PreVendas.size(); i++) {
                CodigoPreVenda= PreVendas.get(i);
                DadosPrevenda = PreVenda.ListarPreVendas(CodigoPreVenda,Loja );
                if(DadosPrevenda.next()){
                   Retorno +=  ManipulacaoString.FormataPADR(15,DadosPrevenda.getLong("codigocotacao") +"-" +DadosPrevenda.getLong("codigoorcamento")," ") ;
                   Retorno +=   ManipulacaoString.FormataPADR(20,TratamentoNulos.getTratarString().Tratar(DadosPrevenda.getString("vendedor"),"")," ");
                   Retorno +=   ManipulacaoString.FormataPADL(11,FormatarNumeros.FormatarParaMoeda(TratamentoNulos.getTratarFloat().Tratar(DadosPrevenda.getFloat("total"),0f)).toString()," ") +  ManipulacaoArquivo2.newline;
                   Total+= DadosPrevenda.getFloat("total");
                }
            }
            Retorno+=ManipulacaoString.FormataPADL(47,"--------------"," ")+  ManipulacaoArquivo2.newline;
            Retorno+="TOTAL" + ManipulacaoString.FormataPADL(22,FormatarNumeros.FormatarParaMoeda(Total), " ")+  ManipulacaoArquivo2.newline;
            
            
            
            
        } catch (Exception e) {
            LogDinnamus.Log(e, true);
        }
        return Retorno;
    } 
}


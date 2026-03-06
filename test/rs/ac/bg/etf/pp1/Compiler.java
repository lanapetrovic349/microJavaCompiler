package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.*;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(Compiler.class);
		Reader br = null;

		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());

			br = new BufferedReader(new FileReader(sourceCode));

			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
			Symbol currToken = p.parse();

			Program prog = (Program) (currToken.value);

			log.info(prog.toString(""));

			Tab.init();

			Struct boolType = new Struct(Struct.Bool);
			Obj boolObj = Tab.insert(Obj.Type, "bool", boolType);
			boolObj.setAdr(-1);
			boolObj.setLevel(-1);

			SemanticAnalyzer semAnalyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(semAnalyzer);

			Tab.dump();

			if (!p.isError && !semAnalyzer.isError()) {
				File objFile = new File("test/program.obj");
				if (objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = semAnalyzer.nVars;
				Code.mainPc = codeGenerator.getmainPc();
				Code.write(new FileOutputStream(objFile));
				
				log.info("Generisanje koda uspesno zavrseno.");
			} else {
				log.error("Generisanje koda nije uspesno zavrseno.");
			}
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}
	}
}

package Sign;

import org.junit.Test;

import it.unive.lisa.AnalysisException;
import it.unive.lisa.LiSA;
import it.unive.lisa.LiSAConfiguration;
import it.unive.lisa.LiSAFactory;
import it.unive.lisa.analysis.AbstractState;
import it.unive.lisa.analysis.heap.HeapDomain;
import it.unive.lisa.analysis.nonrelational.value.SignDomain;
import it.unive.lisa.imp.IMPFrontend;
import it.unive.lisa.imp.ParsingException;

public class SignDomainTest {

	@Test
	public void testSigns() throws  AnalysisException, ParsingException  {
		
	
		LiSAConfiguration conf = new LiSAConfiguration();
		conf.setDumpAnalysis(true);
		conf.setWorkdir("test-outputs/sign");

		conf.setAbstractState(LiSAFactory.getDefaultFor(AbstractState.class, 
				LiSAFactory.getDefaultFor(HeapDomain.class),
				new SignDomain()));
		LiSA lisa = new LiSA(conf);

		lisa.run(IMPFrontend.processFile("imp-testcases/sign/program.imp"));
			
	}
}

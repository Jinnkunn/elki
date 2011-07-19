package de.lmu.ifi.dbs.elki.workflow;

import java.util.ArrayList;
import java.util.List;

import de.lmu.ifi.dbs.elki.result.HierarchicalResult;
import de.lmu.ifi.dbs.elki.result.ResultHandler;
import de.lmu.ifi.dbs.elki.result.ResultWriter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.AbstractParameterizer;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.OptionID;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization.Parameterization;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameters.ObjectListParameter;
import de.lmu.ifi.dbs.elki.visualization.gui.ResultVisualizer;

/**
 * The "output" step, where data is analyzed.
 * 
 * @author Erich Schubert
 * 
 * @apiviz.uses Result
 * @apiviz.has ResultHandler
 */
public class OutputStep implements WorkflowStep {
  /**
   * Output handler.
   */
  private List<ResultHandler> resulthandlers = null;

  /**
   * Constructor.
   * 
   * @param resulthandlers Result handlers to use
   */
  public OutputStep(List<ResultHandler> resulthandlers) {
    super();
    this.resulthandlers = resulthandlers;
  }

  /**
   * Run the result handlers.
   * 
   * @param result Result to run on
   */
  public void runResultHandlers(HierarchicalResult result) {
    // Run result handlers
    for(ResultHandler resulthandler : resulthandlers) {
      resulthandler.processNewResult(result, result);
    }
  }

  /**
   * Set the default handler to the {@link ResultWriter}.
   */
  public static void setDefaultHandlerWriter() {
    defaultHandlers = new ArrayList<Class<? extends ResultHandler>>(1);
    defaultHandlers.add(ResultWriter.class);
  }

  /**
   * Set the default handler to the {@link ResultVisualizer}.
   */
  public static void setDefaultHandlerVisualizer() {
    defaultHandlers = new ArrayList<Class<? extends ResultHandler>>(1);
    defaultHandlers.add(ResultVisualizer.class);
  }
  
  protected static ArrayList<Class<? extends ResultHandler>> defaultHandlers = null;

  /**
   * Parameterization class.
   * 
   * @author Erich Schubert
   * 
   * @apiviz.exclude
   */
  public static class Parameterizer extends AbstractParameterizer {
    /**
     * Output handlers.
     */
    private List<ResultHandler> resulthandlers = null;

    @Override
    protected void makeOptions(Parameterization config) {
      super.makeOptions(config);
      // result handlers
      final ObjectListParameter<ResultHandler> resultHandlerParam = new ObjectListParameter<ResultHandler>(OptionID.RESULT_HANDLER, ResultHandler.class);
      if (defaultHandlers != null) {
        resultHandlerParam.setDefaultValue(defaultHandlers);
      }
      if(config.grab(resultHandlerParam)) {
        resulthandlers = resultHandlerParam.instantiateClasses(config);
      }
    }

    @Override
    protected OutputStep makeInstance() {
      return new OutputStep(resulthandlers);
    }
  }
}
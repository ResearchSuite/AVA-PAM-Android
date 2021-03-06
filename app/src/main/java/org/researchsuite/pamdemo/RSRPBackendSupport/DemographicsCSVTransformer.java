package org.researchsuite.pamdemo.RSRPBackendSupport;

import android.support.annotation.Nullable;

import org.researchstack.backbone.result.StepResult;
import org.researchsuite.rsrp.Core.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;
import org.researchsuite.rsrp.Core.RSRPIntermediateResult;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by christinatsangouri on 4/20/18.
 */

public class DemographicsCSVTransformer implements RSRPFrontEnd {

    @Nullable
    public <T> T extractResult(Map<String, Object> parameters, String identifier) {

        Object param = parameters.get(identifier);
        if (param != null && (param instanceof StepResult)) {
            StepResult stepResult = (StepResult)param;
            if (stepResult.getResult() != null) {
                return (T)stepResult.getResult();
            }
        }
        return null;
    }

    @Nullable
    @Override
    public RSRPIntermediateResult transform(String taskIdentifier, UUID taskRunUUID, Map<String, Object> parameters) {
        String icecream = extractResult(parameters,"icecream");
        String food = extractResult(parameters,"food");


        DemographicsCSVEncodable result = new DemographicsCSVEncodable(
                UUID.randomUUID(),
                taskIdentifier,
                taskRunUUID,
                icecream,
                food
        );

        StepResult firstStepResult = (StepResult) (parameters.get("icecream") != null ? parameters.get("icecream") : parameters.get("food"));

        if (firstStepResult != null) {
            result.setStartDate(firstStepResult.getStartDate());
        }
        else {
            result.setStartDate(new Date());
        }

        if (firstStepResult != null) {
            result.setEndDate(firstStepResult.getStartDate());
        }
        else {
            result.setEndDate(new Date());
        }

        return result;
    }

    @Override
    public boolean supportsType(String type) {
        if (type.equals(org.researchsuite.pamdemo.RSRPBackendSupport.DemographicsCSVEncodable.TYPE)) return true;
        else return false;
    }
}

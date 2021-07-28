package org.bigfoot.swingplus.configurable.components.step;

import lombok.Getter;
import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.JPToolBar;
import org.bigfoot.swingplus.configurable.components.JPClickableLabel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 06/11/2020
 */
public class JPStepPanel<COMPONENT extends Component> extends JPPanel {

    @Getter
    private final List<JPStep<COMPONENT>> steps;

    @Getter
    private JPStep<COMPONENT> selectedStep;

    private JPPanel stepsPanel;

    @SafeVarargs
    public JPStepPanel(JPStep<COMPONENT>... steps) {
        this(Arrays.asList(steps));
    }

    public JPStepPanel(List<JPStep<COMPONENT>> steps) {
        super(new BorderLayout());
        this.steps = steps;
        if (steps.size() > 0) {
            selectedStep = steps.get(0);
        }
        stepsPanel = new JPPanel(new FlowLayout());

        for (JPStep<COMPONENT> step : steps) {
            stepsPanel.add(step.getComponent());
            step.getComponent().setVisible(step.equals(selectedStep));
        }

        add(stepsPanel, BorderLayout.CENTER);
        add(getToolbar(), BorderLayout.NORTH);
        add(getToolbar(), BorderLayout.SOUTH);
    }

    private JPToolBar getToolbar() {
        JPToolBar tb = new JPToolBar();
        tb.setFloatable(false);
        for (JPStep<COMPONENT> step : steps) {
            tb.add(new JPClickableLabel(step.getNaam() + " ", e -> setSelectedStep(step)) {
                @Override
                public void onConfigure() {
                    super.onConfigure();
                    setEnabled(step.equals(selectedStep));
                }
            });
        }
        return tb;
    }

    public void setSelectedStep(String stepName) {
        int index = steps.indexOf(stepName);
        if (index >= 0)
            setSelectedStep(steps.get(index));
    }

    public void setSelectedStep(JPStep<COMPONENT> step) {
        if (steps.contains(step)) {
            selectedStep = step;
            for (JPStep<COMPONENT> s : steps) {
                s.getComponent().setVisible(s.equals(selectedStep));
            }
        }
        onConfigure();
    }

}

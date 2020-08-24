package com.vk.planner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.planner.domain.Plan;
import com.vk.planner.exception.DuplicatePlanFoundException;
import com.vk.planner.exception.PlanNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanService {

    private List<Plan> getPlansList() {
        List<Plan> plans = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(getClass().getClassLoader().getResource("allPlans.json").getFile());
            Plan[] plans1 = objectMapper.readValue(file, Plan[].class);
            plans = new ArrayList<>();
            plans.addAll(Arrays.asList(plans1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plans;
    }

    private void savePlans(final List<Plan> plans) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter();
            File file = new File(getClass().getClassLoader().getResource("allPlans.json").getFile());
            objectMapper.writeValue(file, plans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Plan getPlanForId(final List<Plan> plans, final Long id) {
        Plan plan1 = plans.stream().filter(plan -> plan.getId().equals(id)).findFirst().orElse(null);
        return plan1;
    }

    public Plan addPlan(final Plan plan) {
        List<Plan> plans = getPlansList();
        List<Long> planIds = plans.stream().map(Plan::getId).collect(Collectors.toList());
        Long maxPlanId = !planIds.isEmpty() ? planIds.get(planIds.size() - 1) : -1L;

        if (!plans.contains(plan)) {
            plan.setId(++maxPlanId);
            plans.add(plan);
            savePlans(plans);
            return plan;
        }

        throw new DuplicatePlanFoundException("Plan already exists: ");
    }

    public Boolean updatePlan(final Long id, final Plan plan) {
        List<Plan> plans = getPlansList();
        List<Long> planIds = plans.stream().map(Plan::getId).collect(Collectors.toList());
        if (!planIds.contains(id)) {
            throw new PlanNotFoundException("No plan found for id : " + id);
        } else {
            Plan oldPlan = getPlanForId(plans, id);
            plans.remove(oldPlan);
            plans.add(plan);
            savePlans(plans);
        }
        return true;
    }

    public Boolean deletePlan(final Long id) {
        List<Plan> plans = getPlansList();
        List<Long> planIds = plans.stream().map(Plan::getId).collect(Collectors.toList());
        if (!planIds.contains(id)) {
            throw new PlanNotFoundException("No plan found for id : " + id);
        } else {
            Plan oldPlan = getPlanForId(plans, id);
            plans.remove(oldPlan);
            savePlans(plans);
        }
        return true;
    }

    public Boolean deletePlans(List<String> ids) {
        List<Plan> plans = getPlansList();
        List<Long> planIds = plans.stream().map(Plan::getId).collect(Collectors.toList());
        for (String id : ids) {
            if (planIds.contains(Long.parseLong(id))) {
                Plan oldPlan = getPlanForId(plans, Long.parseLong(id));
                plans.remove(oldPlan);
            }
        }
        savePlans(plans);
        return true;
    }

    public Plan getPlan(final Long id) {
        List<Plan> plans = getPlansList();
        List<Long> planIds = plans.stream().map(Plan::getId).collect(Collectors.toList());
        if (planIds.contains(id)) {
            Plan plan = getPlanForId(plans, id);
            return plan;
        }
        throw new PlanNotFoundException("No plan found for id : " + id);
    }

    public Collection<Plan> getPlans(final String type) {
        List<Plan> plans = getPlansList();
        if (StringUtils.isEmpty(type)) {
            return plans;
        }

        List<Plan> plansOfType = plans.stream()
                .filter(plan -> plan.getType().equals(type))
                .collect(Collectors.toList());
        return plansOfType;
    }
}

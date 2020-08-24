package com.vk.planner.controller;

import com.vk.planner.domain.Plan;
import com.vk.planner.service.PlanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v1/plans")
@AllArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public Plan createPlan(@RequestBody final Plan plan) {
        Plan planCreated = planService.addPlan(plan);
        return planCreated;
    }

    @PutMapping(value = "/{id}")
    public Boolean updatePlan(@PathVariable("id") final Long id,
                              @RequestBody final Plan plan) {
        Boolean updated = planService.updatePlan(id, plan);
        return updated;
    }

    @DeleteMapping(value = "/{id}")
    public Boolean deletePlan(@PathVariable("id") final Long id) {
        Boolean deleted = planService.deletePlan(id);
        return deleted;
    }

    @DeleteMapping
    public Boolean deletePlans(@RequestParam(name="ids") List<String> ids) {
        Boolean deleted = planService.deletePlans(ids);
        return deleted;
    }

    @GetMapping(value = "/{id}")
    public Plan getPlan(@PathVariable("id") final Long id) {
        Plan plan = planService.getPlan(id);
        return plan;
    }

    @GetMapping
    public Collection<Plan> getPlansOfAType(@RequestParam(name="type", defaultValue = "") String type) {
        Collection<Plan> plans = planService.getPlans(type);
        return plans;
    }
}

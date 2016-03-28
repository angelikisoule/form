package gr.soule.form.core.controllers.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author nk, npapadopoulos
 */
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {

	@Autowired SessionFactory sessionFactory;
	private Statistics statistics;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String statistics(Model model) {
    	Map<String, SecondLevelCacheStatistics> regionStatistics = new HashMap<String, SecondLevelCacheStatistics>();
    	Statistics statistics = getStatistics();
    	long elementsSizeInMemory = 0;
    	long elementsCountInMemory = 0;
    	long elementsCountOnDisk = 0;
    	for(String name : statistics.getSecondLevelCacheRegionNames()) {
			elementsSizeInMemory += statistics.getSecondLevelCacheStatistics(name).getSizeInMemory();
			elementsCountInMemory += statistics.getSecondLevelCacheStatistics(name).getElementCountInMemory();
			elementsCountOnDisk += statistics.getSecondLevelCacheStatistics(name).getElementCountOnDisk();
			/*
			 * Second Level Cache Statistics For Each Region
			 */
			regionStatistics.put(name, statistics.getSecondLevelCacheStatistics(name));
    	}
    	model.addAttribute("queryExecutionMaxTimeQueryString", statistics.getQueryExecutionMaxTimeQueryString());
    	model.addAttribute("elementsSizeInMemory", elementsSizeInMemory);
    	model.addAttribute("startTime", new Date(statistics.getStartTime()));
    	model.addAttribute("elementsCountInMemory", elementsCountInMemory);
		model.addAttribute("elementsCountOnDisk", elementsCountOnDisk);
		model.addAttribute("regionStatistics", regionStatistics);
		model.addAttribute("statistics", statistics);

		return "admin/statistics/list";
    }
    
    @RequestMapping(value = "clear", method = RequestMethod.GET)
    public String clear(Model model) {
    	getStatistics().clear();
    	return "redirect:/admin/statistics/list";
    }

    /**
     * Get Hibernate's Statistics
     * @return Hibernate Statistics
     */
    private Statistics getStatistics() {
    	this.statistics = sessionFactory.getStatistics();
    	return statistics;
    }
}

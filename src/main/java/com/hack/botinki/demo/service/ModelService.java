package com.hack.botinki.demo.service;

import org.dmg.pmml.Model;
import org.dmg.pmml.PMML;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

import com.hack.botinki.demo.entity.Task;

import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.FieldValue;

import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;

public class ModelService {
	
	private final TaskService taskService;
	private ModelEvaluator<?> evaluator;
	
	public ModelService(TaskService taskService) {
		 this.taskService = taskService;
		 PMML pmml;
		 try {
			pmml = PMMLUtil.unmarshal(new FileInputStream(new File("resources/model.pmml")));
			Model model = pmml.getModels().get(0);
			ModelEvaluator<?> evaluator = ModelEvaluatorFactory.newInstance()
				    .newModelEvaluator(pmml, model);
			evaluator.verify();
			///логи прописать
		 } catch (FileNotFoundException | ParserConfigurationException | SAXException | JAXBException e) {
			///здесь тож
			e.printStackTrace();
		 }		
	}
	
	
	public int[] execute(Long id) {
		List<Task> tasks = taskService.getTasksByUserId(id);
		HashMap<Long, Double> priorityByID = new HashMap<>();
		for (Task task : tasks) {
			priority = predict(task);
		}
		
	}
	
	private int predict(Task task) {
		ModelEvaluator<?> evaluator = this.evaluator;
		task.getId();
	
	}
	
	
}

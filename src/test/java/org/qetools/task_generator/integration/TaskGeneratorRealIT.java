/*******************************************************************************
 * Copyright (C) 2018 Andrej Podhradsky
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.qetools.task_generator.integration;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.qetools.task_generator.TaskGeneratorApp.SYSTEM_PROPERTY_CONFIG;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.rules.ErrorCollector;
import org.qetools.task_generator.TaskGeneratorApp;

public class TaskGeneratorRealIT {

	public static final String PROJECT = "MYPROJECT";

	private static String configFile;

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Rule
	public SystemErrRule systemErrRuleLog = new SystemErrRule().enableLog();

	@BeforeClass
	public static void checkConfigFile() {
		configFile = System.getProperty(SYSTEM_PROPERTY_CONFIG);
		assumeNotNull("System property '" + SYSTEM_PROPERTY_CONFIG + "' not defined", configFile);
		assumeTrue("File '" + configFile + "' doesn't exists", new File(configFile).exists());
	}

	@Test
	public void simpleTest() throws Exception {
		generate("template-task-real.yaml");

		// SimpleLogger logs to System.err
		String[] lines = systemErrRuleLog.getLogWithNormalizedLineSeparator().split("\n");
		for (String line : lines) {
			if (line.contains("created")) {
				String key = line.substring(line.lastIndexOf("key = ") + 6);
				System.out.println("> " + key);
			}
		}
	}

	protected void generate(String yamlFile) throws IOException {
		generate(yamlFile, configFile);
	}

	protected void generate(String yamlFile, String configFile) throws IOException {
		TaskGeneratorApp.main(new String[] { getFile(yamlFile).getAbsolutePath() });
	}

	protected File getFile(String fileName) {
		return new File(getClass().getClassLoader().getResource(fileName).getFile());
	}
}

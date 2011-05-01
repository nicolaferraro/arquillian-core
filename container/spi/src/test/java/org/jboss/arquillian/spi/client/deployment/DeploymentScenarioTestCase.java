/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.spi.client.deployment;

import junit.framework.Assert;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.cdi.beans.BeansDescriptor;
import org.junit.Test;

/**
 * DeploymentScenarioTest
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class DeploymentScenarioTestCase
{

   /**
    * Defaulting rules for Deployment in a scenario
    * 
    * - A single Archive is default
    * - A Archive and a Descriptor, Archive is default
    * 
    */

   @Test
   public void shouldDefaultToSingleArchive()
   {
      DeploymentDescription deployment = new DeploymentDescription("_DEFAULT_", ShrinkWrap.create(JavaArchive.class));
      deployment.setTarget(TargetDescription.DEFAULT);
      
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(deployment);
      
      DeploymentDescription defaultDeployment = scenario.getDeployment(DeploymentTargetDescription.DEFAULT).getDescription();
      
      Assert.assertEquals(deployment, defaultDeployment);
   }

   @Test
   public void shouldDefaultToSingleDescriptor()
   {
      DeploymentDescription deployment = new DeploymentDescription("_DEFAULT_", Descriptors.create(BeansDescriptor.class));
      deployment.setTarget(TargetDescription.DEFAULT);
      
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(deployment);
      
      DeploymentDescription defaultDeployment = scenario.getDeployment(DeploymentTargetDescription.DEFAULT).getDescription();
      
      Assert.assertEquals(deployment, defaultDeployment);
   }

   @Test
   public void shouldDefaultToSingleArchiveWithDescriptor()
   {
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(
            new DeploymentDescription("A", ShrinkWrap.create(JavaArchive.class))
            .setTarget(TargetDescription.DEFAULT));
      scenario.addDeployment(
            new DeploymentDescription("B", Descriptors.create(BeansDescriptor.class))
            .setTarget(TargetDescription.DEFAULT));


      DeploymentDescription defaultDeployment = scenario.getDeployment(DeploymentTargetDescription.DEFAULT).getDescription();
      
      Assert.assertEquals("A", defaultDeployment.getName());
   }

   @Test
   public void shouldNotDefaultWhenMultipleArchives()
   {
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(
            new DeploymentDescription("A", ShrinkWrap.create(JavaArchive.class))
            .setTarget(TargetDescription.DEFAULT));
      scenario.addDeployment(
            new DeploymentDescription("B", ShrinkWrap.create(JavaArchive.class))
            .setTarget(TargetDescription.DEFAULT));
      
      Deployment defaultDeployment = scenario.getDeployment(DeploymentTargetDescription.DEFAULT);
      
      Assert.assertNull(defaultDeployment);
   }
   
   @Test
   public void shouldNotDefaultWhenMultipleDescriptors()
   {
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(
            new DeploymentDescription("A", Descriptors.create(BeansDescriptor.class))
            .setTarget(TargetDescription.DEFAULT));
      scenario.addDeployment(
            new DeploymentDescription("B", Descriptors.create(BeansDescriptor.class))
            .setTarget(TargetDescription.DEFAULT));
      
      Deployment defaultDeployment = scenario.getDeployment(DeploymentTargetDescription.DEFAULT);
      
      Assert.assertNull(defaultDeployment);
   }

   @Test
   public void shouldNotGetDefaultWithNonDefaultName()
   {
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(
            new DeploymentDescription("A", ShrinkWrap.create(JavaArchive.class))
            .setTarget(TargetDescription.DEFAULT));
      scenario.addDeployment(
            new DeploymentDescription("B", Descriptors.create(BeansDescriptor.class))
            .setTarget(TargetDescription.DEFAULT));


      DeploymentDescription deployment = scenario.getDeployment(new DeploymentTargetDescription("B")).getDescription();
      
      Assert.assertEquals("B", deployment.getName());
   }

   @Test
   public void shouldNotGetWithUnknownName()
   {
      DeploymentScenario scenario = new DeploymentScenario();
      scenario.addDeployment(
            new DeploymentDescription("A", ShrinkWrap.create(JavaArchive.class))
            .setTarget(TargetDescription.DEFAULT));
      scenario.addDeployment(
            new DeploymentDescription("B", Descriptors.create(BeansDescriptor.class))
            .setTarget(TargetDescription.DEFAULT));


      Deployment deployment = scenario.getDeployment(new DeploymentTargetDescription("C"));
      
      Assert.assertNull(deployment);
      
   }
}

/*
 * Copyright 2009 JBoss, a divison Red Hat, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.errai.cdi.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.util.AnnotationLiteral;

import org.jboss.errai.bus.client.framework.MessageBus;
import org.jboss.errai.bus.server.api.ServerMessageBus;
import org.jboss.errai.bus.server.service.ErraiService;
import org.jboss.errai.bus.server.service.ErraiServiceImpl;
import org.jboss.errai.bus.server.service.ErraiServiceSingleton;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

/**
 * @author Heiko Braun <hbraun@redhat.com>
 */
public class ErraiServiceBean implements Bean {

  final InjectionTarget it;

  static final Set<Annotation> qualifiers = unmodifiableSet(new HashSet<Annotation>(
          asList(new AnnotationLiteral<Default>() {
                 },
                 new AnnotationLiteral<Any>() {
                 }
          )));

  static final Set<Type> types = unmodifiableSet(new HashSet<Type>(
          asList(ErraiService.class, Object.class)));

  @SuppressWarnings("unchecked")
  public ErraiServiceBean(final BeanManager bm) {

    //use this to read annotations of the class
    AnnotatedType at = bm.createAnnotatedType(ErraiServiceImpl.class);

    //use this to create the class and inject dependencies
    this.it = bm.createInjectionTarget(at);
  }

  public Class<?> getBeanClass() {
    return ErraiService.class;
  }

  @SuppressWarnings("unchecked")
  public Set<InjectionPoint> getInjectionPoints() {
    return it.getInjectionPoints();
  }

  public String getName() {
    return "Errai";
  }

  public Set<Annotation> getQualifiers() {
  return qualifiers;
  }

  public Class<? extends Annotation> getScope() {
    return ApplicationScoped.class;
  }

  public Set<Class<? extends Annotation>> getStereotypes() {
    return Collections.emptySet();
  }

  public Set<Type> getTypes() {
    return types;
  }

  public boolean isAlternative() {
    return false;
  }

  public boolean isNullable() {
    return false;
  }

  @SuppressWarnings("unchecked")
  public Object create(CreationalContext ctx) {
    Object instance = ErraiServiceSingleton.getService();
    it.inject(instance, ctx);
    it.postConstruct(instance);
    return instance;
  }

  @SuppressWarnings("unchecked")
  public void destroy(Object instance, CreationalContext ctx) {
    it.preDestroy(instance);
    it.dispose(instance);
    ctx.release();
  }
}

/*
 * Copyright (C) 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.errai.jpa.client.local;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Extends the JPA SingularAttribute interface with methods required by Errai
 * persistence but missing from the JPA metamodel.
 *
 * @param <X>
 *          The type containing the represented attribute
 * @param <T>
 *          The type of the represented attribute
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
public interface ErraiSingularAttribute<X, T> extends ErraiAttribute<X, T>, SingularAttribute<X, T> {

  /**
   * Can the attribute's value be generated (usually for ID attributes).
   */
  public boolean isGeneratedValue();

  /**
   * Returns a generator for the values of this attribute. Only works for
   * attributes that are annotated with {@code @GeneratedValue}.
   *
   * @return the ID generator for this generated attribute. Never null.
   * @throws UnsupportedOperationException
   *           if this attribute is not a {@code @GeneratedValue}.
   */
  public ErraiIdGenerator<T> getValueGenerator();
}

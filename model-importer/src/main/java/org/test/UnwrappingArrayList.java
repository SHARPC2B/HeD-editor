/*
 * Copyright 2013 JBoss Inc
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

package org.test;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;

public class UnwrappingArrayList<T> extends ArrayList<T> {

    private List unwrapped = new ArrayList();

    public UnwrappingArrayList() {
        super();
    }

    public boolean add( T elem ) {
        boolean b = super.add( elem );
        if ( b ) {
            if ( elem instanceof JAXBElement ) {
                unwrapped.add( ( (JAXBElement) elem ).getValue() );
            } else {
                unwrapped.add( elem );
            }
        }
        return b;
    }

    public boolean remove( Object elem ) {
        boolean b = super.remove( elem );
        if ( b ) {
            if ( elem instanceof JAXBElement ) {
                unwrapped.remove( ((JAXBElement) elem).getValue() );
            } else {
                unwrapped.remove( elem );
            }
        }
        return b;
    }

    public boolean contains( Object elem ) {
        boolean b = super.contains( elem );
        if ( ! b && elem instanceof JAXBElement ) {
            b |= unwrapped.contains( ( (JAXBElement) elem ).getValue() );
        }
        return b;
    }

    public List asUnwrapped() {
        return unwrapped;
    }


}

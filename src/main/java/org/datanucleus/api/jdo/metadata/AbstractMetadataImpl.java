/**********************************************************************
Copyright (c) 2009 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.datanucleus.api.jdo.metadata;

import javax.jdo.metadata.ExtensionMetadata;
import javax.jdo.metadata.Metadata;

import org.datanucleus.metadata.ExtensionMetaData;
import org.datanucleus.metadata.MetaData;

/**
 * Base for all implementations of JDO Metadata classes. Provides parentage, and the underlying
 * internal metadata component used by DataNucleus. Also provides extension handling.
 */
public class AbstractMetadataImpl implements Metadata
{
    /** Link to parent wrapper. */
    AbstractMetadataImpl parent;

    /** DataNucleus internal MetaData object backing this. */
    MetaData internalMD;

    public AbstractMetadataImpl(MetaData internal)
    {
        this.internalMD = internal;
    }

    public String toString()
    {
        return internalMD.toString("", "    ");
    }

    public ExtensionMetadata[] getExtensions()
    {
        ExtensionMetaData[] exts = internalMD.getExtensions();
        if (exts == null)
        {
            return null;
        }
        else
        {
            ExtensionMetadata[] extensions = new ExtensionMetadata[exts.length];
            for (int i=0;i<extensions.length;i++)
            {
                extensions[i] = new ExtensionMetadataImpl(exts[i]);
            }
            return extensions;
        }
    }

    public int getNumberOfExtensions()
    {
        return internalMD.getNoOfExtensions();
    }

    public ExtensionMetadata newExtensionMetadata(String vendor, String key, String value)
    {
        // Create new backing extension, and wrap it for returning
        return new ExtensionMetadataImpl(internalMD.newExtensionMetaData(vendor, key, value));
    }

    public AbstractMetadataImpl getParent()
    {
        return parent;
    }
}
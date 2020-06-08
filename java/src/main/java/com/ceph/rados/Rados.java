package com.ceph.rados;

import java.net.URI;
import java.util.List;
import java.util.Properties;

import com.ceph.rados.impl.Native;
import com.ceph.rados.impl.RadosImpl;

public interface Rados extends AutoCloseable {

    /**
     * Creates an instance of the RADOS client from the supplied Ceph configuration file
     *
     * @param uri the URI to the configuration file
     * @return an instance of the RADOS client
     */
    static Rados fromURI(URI uri) {
        return new RadosImpl(uri);
    }

    /**
     * Creates an instance of the RADOS client from the supplied properties
     *
     * @param properties
     * @return an instance of the RADOS client
     */
    static Rados fromProperties(Properties properties) {
        return new RadosImpl(properties);
    }

    /**
     * Returns the version of the underlying native RADOS library
     *
     * @return a {@link Version} instance
     */
    static Version version() {
        return Native.INSTANCE.version();
    }


    /**
     * Loads a Ceph configuration file
     *
     * @param uri
     * @return
     */
    Rados configurationFile(URI uri);

    /**
     * Sets a configuration property
     *
     * @param name  the name of the property
     * @param value the value of the property
     * @return
     */
    Rados configurationProperty(String name, String value);

    /**
     * Returns whether
     *
     * @return
     */
    boolean isConnected();

    /**
     * Connects to the Ceph cluster. After this method is invoked, {@link #isConnected()} will return true until the
     * {@link #close()} method has been called. {@link #close()} <b>must</b> be invoked to free all associated
     * resources.
     */
    Rados connect();

    /**
     * Create an io context
     * <p>
     * The io context allows you to perform operations within a particular pool.
     *
     * @param poolName the name of the pool.
     * @return an {@link IOCtx} instance. The instance must be {@link #close()}d when finished.
     */
    IOCtx createIOContext(String poolName);

    IOCtx createIOContext(long poolId);

    /**
     * Gets a list of pool names
     *
     * @return a {@link List} of pool names
     */
    List<String> poolNames();

    /**
     * Get the id of a pool
     *
     * @param poolName
     * @return
     */
    long poolLookup(String poolName);

    /**
     * Get the name of a pool
     *
     * @param poolId
     * @return the id of the pool
     */
    String reversePoolLookup(long poolId);

    /**
     * Create a pool with default settings
     *
     * @param poolName
     */
    void poolCreate(String poolName);

    /**
     * Create a pool with default settings
     *
     * @param poolName
     * @param crushRule
     */
    void poolCreate(String poolName, int crushRule);

    /**
     * Delete a pool and all data inside it
     * <p>
     * The pool is removed from the cluster immediately, but the actual data is deleted in the background.
     *
     * @param poolName
     */
    void poolDelete(String poolName);

    /**
     * Get the fsid of the cluster as a hexadecimal string.
     *
     * @return the fsid
     */
    String fsid();
}

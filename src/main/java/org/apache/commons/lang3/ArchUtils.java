/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.arch.Processor;

/**
 * An utility class for the os.arch System Property. The class defines methods for
 * identifying the architecture of the current JVM.
 * <p>
 * Important: The os.arch System Property returns the architecture used by the JVM
 * not of the operating system.
 * </p>
 * @since 3.6
 */
public class ArchUtils {

    private static final Map<String, Processor> ARCH_TO_PROCESSOR;

    static {
        ARCH_TO_PROCESSOR = new HashMap<>();
        init();
    }

    private static void init() {
        init_X86_32Bit();
        init_X86_64Bit();
        init_IA64_32Bit();
        init_IA64_64Bit();
        init_PPC_32Bit();
        init_PPC_64Bit();
    }

    private static void init_X86_32Bit() {
        final Processor processor = new Processor(Processor.Arch.BIT_32, Processor.Type.X86);
        addProcessors(processor, Processor.Key.X86, Processor.Key.I386, Processor.Key.I486, Processor.Key.I586,
            Processor.Key.I686, Processor.Key.PENTIUM);
    }

    private static void init_X86_64Bit() {
        final Processor processor = new Processor(Processor.Arch.BIT_64, Processor.Type.X86);
        addProcessors(processor, Processor.Key.X86_64, Processor.Key.AMD64, Processor.Key.EM64T, Processor.Key.UNIVERSAL);
    }

    private static void init_IA64_32Bit() {
        final Processor processor = new Processor(Processor.Arch.BIT_32, Processor.Type.IA_64);
        addProcessors(processor, Processor.Key.IA64_32, Processor.Key.IA64N);
    }

    private static void init_IA64_64Bit() {
        final Processor processor = new Processor(Processor.Arch.BIT_64, Processor.Type.IA_64);
        addProcessors(processor, Processor.Key.IA64, Processor.Key.IA64W);
    }

    private static void init_PPC_32Bit() {
        final Processor processor = new Processor(Processor.Arch.BIT_32, Processor.Type.PPC);
        addProcessors(processor, Processor.Key.PPC, Processor.Key.POWER, Processor.Key.POWERPC,
            Processor.Key.POWER_PC, Processor.Key.POWER_RS);
    }

    private static void init_PPC_64Bit() {
        final Processor processor = new Processor(Processor.Arch.BIT_64, Processor.Type.PPC);
        addProcessors(processor, Processor.Key.PPC_64, Processor.Key.POWER64, Processor.Key.POWERPC64,
            Processor.Key.POWER_PC64, Processor.Key.POWER_RS64);
    }

    /**
     * Adds the given {@link Processor} with the given key {@link String} to the map.
     *
     * @param key The key as {@link String}.
     * @param processor The {@link Processor} to add.
     * @throws IllegalStateException If the key already exists.
     */
    private static void addProcessor(final String key, final Processor processor) {
        if (ARCH_TO_PROCESSOR.containsKey(key)) {
            throw new IllegalStateException("Key " + key + " already exists in processor map");
        }
        ARCH_TO_PROCESSOR.put(key, processor);
    }

    /**
     * Adds the given {@link Processor} with the given keys to the map.
     *
     * @param keys The keys.
     * @param processor The {@link Processor} to add.
     * @throws IllegalStateException If the key already exists.
     */
    private static void addProcessors(final Processor processor, final Processor.Key... keys) {
        Stream.of(keys).forEach(e -> addProcessor(e.toString().toLowerCase(), processor));
    }

    /**
     * Returns a {@link Processor} object of the current JVM.
     *
     * <p>
     * Important: The os.arch System Property returns the architecture used by the JVM
     * not of the operating system.
     * </p>
     *
     * @return A {@link Processor} when supported, else {@code null}.
     */
    public static Processor getProcessor() {
        return getProcessor(SystemUtils.OS_ARCH);
    }

    /**
     * Returns a {@link Processor} object the given value {@link String}. The {@link String} must be
     * like a value returned by the os.arch System Property.
     *
     * @param value A {@link String} like a value returned by the os.arch System Property.
     * @return A {@link Processor} when it exists, else {@code null}.
     */
    public static Processor getProcessor(final String value) {
        return ARCH_TO_PROCESSOR.get(value);
    }

}

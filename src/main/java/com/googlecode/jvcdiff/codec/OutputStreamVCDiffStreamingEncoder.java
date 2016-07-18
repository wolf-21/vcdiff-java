// Copyright 2007-2016 Google Inc., David Ehrmann
// Author: Lincoln Smith, David Ehrmann
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
// Classes to implement an Encoder for the format described in
// RFC 3284 - The VCDIFF Generic Differencing and Compression Data Format.
// The RFC text can be found at http://www.faqs.org/rfcs/rfc3284.html
//
// The RFC describes the possibility of using a secondary compressor
// to further reduce the size of each section of the VCDIFF output.
// That feature is not supported in this implementation of the encoder
// and decoder.
// No secondary compressor types have been publicly registered with
// the IANA at http://www.iana.org/assignments/vcdiff-comp-ids
// in the more than five years since the registry was created, so there
// is no standard set of compressor IDs which would be generated by other
// encoders or accepted by other decoders.

package com.googlecode.jvcdiff.codec;

import com.googlecode.jvcdiff.CodeTableWriterInterface;
import com.googlecode.jvcdiff.VCDiffCodeTableWriter;
import com.googlecode.jvcdiff.google.VCDiffFormatExtensionFlag;

import java.io.OutputStream;
import java.util.EnumSet;

import static com.googlecode.jvcdiff.google.VCDiffFormatExtensionFlag.VCD_FORMAT_INTERLEAVED;
import static com.googlecode.jvcdiff.google.VCDiffFormatExtensionFlag.VCD_FORMAT_JSON;

public class OutputStreamVCDiffStreamingEncoder extends BaseVCDiffStreamingEncoder<OutputStream> {

    public OutputStreamVCDiffStreamingEncoder(HashedDictionary dictionary,
                                              EnumSet<VCDiffFormatExtensionFlag> format_extensions,
                                              boolean look_for_target_matches) {
        super(buildCodeTableWriter(format_extensions), dictionary, format_extensions, look_for_target_matches);
    }

    private static CodeTableWriterInterface<OutputStream> buildCodeTableWriter(EnumSet<VCDiffFormatExtensionFlag> format_extensions) {
        if (format_extensions.contains(VCD_FORMAT_JSON)) {
            throw new IllegalArgumentException("Requesting " + VCD_FORMAT_JSON + " for " + OutputStreamVCDiffStreamingEncoder.class.getSimpleName());
        } else {
            // This implementation of the encoder uses the default
            // code table.  A VCDiffCodeTableWriter could also be constructed
            // using a custom code table.
            return new VCDiffCodeTableWriter(format_extensions.contains(VCD_FORMAT_INTERLEAVED));
        }
    }
}

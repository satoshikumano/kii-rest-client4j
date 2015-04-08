package com.kii.cloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	public static long copy(final InputStream input, final OutputStream output) throws IOException {
		byte[] buffer = new byte[1024 * 4];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}

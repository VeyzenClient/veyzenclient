package com.github.veyzenclient.veyzenclient.init;

import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.spongepowered.asm.lib.tree.*;

/**
 * A mixin plugin to automatically discover all mixins in the current JAR.
 * Only supports mixin package and its subpackages.
 */
public class AutoDiscoveryMixinPlugin implements IMixinConfigPlugin {

    private static final List<AutoDiscoveryMixinPlugin> mixinPlugins = new ArrayList<>();

    public static List<AutoDiscoveryMixinPlugin> getMixinPlugins() {
        return mixinPlugins;
    }

    private String mixinPackage;
    private List<String> mixins = null;

    @Override
    public void onLoad(String mixinPackage) {
        this.mixinPackage = mixinPackage;
        mixinPlugins.add(this);
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
	
    @Override
    public List<String> getMixins() {
        if (mixins != null) return mixins;

        System.out.println("Discovering mixins...");
        mixins = new ArrayList<>();

        URL classUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        Path basePath;
        try {
            basePath = Paths.get(getBaseUrlForClassUrl(classUrl).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid base URL for class", e);
        }

        if (Files.isDirectory(basePath)) {
            walkDir(basePath);
        } else {
            walkJar(basePath);
        }

        System.out.println("Mixins found: " + mixins);
        return mixins;
    }

    private void walkDir(Path classRoot) {
		System.out.println("Searching mixins from directory...");
		Path mixinPath = classRoot.resolve(getMixinBaseDir());

		if (!Files.exists(mixinPath)) return;

		List<Path> paths = new ArrayList<>();
		collectPaths(mixinPath, paths);

		for (int i = 0; i < paths.size(); i++) {
			Path path = paths.get(i);
			String relative = classRoot.relativize(path).toString();
			tryAddMixinClass(relative);
		}
	}

	private void collectPaths(Path dir, List<Path> paths) {
		try {
			if (Files.isDirectory(dir)) {
				java.io.File[] files = dir.toFile().listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						java.io.File file = files[i];
						Path path = file.toPath();
						if (file.isDirectory()) {
							collectPaths(path, paths);
						} else {
							paths.add(path);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to walk directory for mixins", e);
		}
	}

    private void walkJar(Path file) {
        System.out.println("Searching mixins from JAR...");
        try {
            ZipInputStream zis = new ZipInputStream(Files.newInputStream(file));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                tryAddMixinClass(entry.getName());
                zis.closeEntry();
            }
            zis.close();
        } catch (IOException e) {
            throw new RuntimeException("Error reading mixins from JAR", e);
        }
    }

    private void tryAddMixinClass(String className) {
        if (className == null || !className.endsWith(".class")) return;

        String norm = className.substring(0, className.length() - 6) // remove .class
			.replace("\\", "/")
			.replace("/", ".");

        if (norm.startsWith(mixinPackage + ".") && !norm.endsWith(".")) {
            String mixinClass = norm.substring(mixinPackage.length() + 1);
            mixins.add(mixinClass);
        }
    }

    private URL getBaseUrlForClassUrl(URL classUrl) {
        String urlStr = classUrl.toString();
        if (urlStr.startsWith("jar:")) {
            try {
                return new URL(urlStr.substring(4, urlStr.indexOf("!")));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed JAR URL", e);
            }
        }

        if (urlStr.endsWith(".class")) {
            String className = getClass().getCanonicalName().replace(".", "/") + ".class";
            try {
                return new URL(urlStr.replace(className, ""));
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed class URL", e);
            }
        }

        return classUrl;
    }

    private String getMixinBaseDir() {
        return mixinPackage.replace(".", "/");
    }

    public String getMixinPackage() {
        return mixinPackage;
    }
}

package pl.edu.wat.projektspring.reflection;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Reflection {
    private TypeDescription entityDefinition;
    private TypeDescription requestDefinition;
    private TypeDescription responseDefinition;
    private TypeDescription mapperDefinition;
    private TypePool typePool;
    private ByteBuddy byteBuddy;

    public Reflection() {
        this.typePool = TypePool.Default.ofSystemLoader();
        this.byteBuddy = new ByteBuddy();
        this.entityDefinition = typePool.describe("pl.edu.wat.projektspring.entity.Company").resolve();
        this.requestDefinition = typePool.describe("pl.edu.wat.projektspring.dto.CompanyRequest").resolve();
        this.responseDefinition = typePool.describe("pl.edu.wat.projektspring.dto.CompanyResponse").resolve();
        this.mapperDefinition = typePool.describe("pl.edu.wat.projektspring.mapper.CompanyMapper").resolve();

    }

    public static void apply(String fieldName, String fieldType) {
        var ref = new Reflection();
        ref.applyEntity(fieldName, fieldType);
        ref.applyRequest(fieldName, fieldType);
        ref.applyResponse(fieldName, fieldType);
        ref.applyCompanyMapper(fieldName);
    }

    private void applyCompanyMapper(String fieldName) {
        //TypePool typePool = TypePool.Default.ofSystemLoader();
        ByteBuddy byteBuddy = new ByteBuddy();
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(mapperDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .method(named("fillCompanyRequest"))
                .intercept(MethodCall.invoke(setterCompanyEntity(fieldName))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterRequest(fieldName))
                                .onArgument(1)))
                .method(named("fillCompany"))
                .intercept(MethodCall.invoke(setterCompanyResponse(fieldName))
                        .onArgument(0)
                        .withMethodCall(MethodCall
                                .invoke(getterEntity(fieldName))
                                .onArgument(1)));

        try (var unloadedCompany = builder.make()) {
            mapperDefinition =  unloadedCompany.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MethodDescription getterEntity(String fieldName) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterCompanyResponse(String fieldName) {
        return responseDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription getterRequest(String fieldName) {
        return requestDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isGetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private MethodDescription setterCompanyEntity(String fieldName) {
        return entityDefinition
                .getDeclaredMethods()
                .filter(ElementMatchers.isSetter(fieldName))
                .stream()
                .findFirst()
                .orElseThrow();
    }


    private void applyResponse(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(responseDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedCompany = builder.make()) {
            responseDefinition = unloadedCompany.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void applyRequest(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(requestDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedCompany = builder.make()) {
            requestDefinition = unloadedCompany.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void applyEntity(String fieldName, String fieldType) {
        DynamicType.Builder<Object> builder = byteBuddy
                .redefine(entityDefinition,
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineProperty(fieldName, typePool.describe(fieldType).resolve());

        try (var unloadedCompany = builder.make()) {
            entityDefinition = unloadedCompany.load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getTypeDescription();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
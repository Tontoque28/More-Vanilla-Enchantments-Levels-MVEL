package com.tontoque28.mvel.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class MVELTransformer implements IClassTransformer {
    private static final String PREFIX = "net.minecraft.enchantment.Enchantment";
    private static final String HOOK_OWNER = "com/tontoque28/mvel/MVEL";
    private static final String HOOK_NAME = "getMaxLevelFor";
    private static final String HOOK_DESC = "(Lnet/minecraft/enchantment/Enchantment;I)I";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null || transformedName == null || !transformedName.startsWith(PREFIX)) {
            return basicClass;
        }
        ClassNode node = new ClassNode();
        new ClassReader(basicClass).accept(node, 0);
        boolean changed = false;
        for (MethodNode method : node.methods) {
            if (!"()I".equals(method.desc)) continue;
            if (!"getMaxLevel".equals(method.name) && !"func_77325_b".equals(method.name)) continue;
            if ((method.access & Opcodes.ACC_STATIC) != 0 || (method.access & Opcodes.ACC_ABSTRACT) != 0) continue;
            AbstractInsnNode insn = method.instructions.getFirst();
            while (insn != null) {
                AbstractInsnNode next = insn.getNext();
                if (insn.getOpcode() == Opcodes.IRETURN) {
                    method.instructions.insertBefore(insn, new VarInsnNode(Opcodes.ALOAD, 0));
                    method.instructions.insertBefore(insn, new InsnNode(Opcodes.SWAP));
                    method.instructions.insertBefore(insn, new MethodInsnNode(Opcodes.INVOKESTATIC, HOOK_OWNER, HOOK_NAME, HOOK_DESC, false));
                    changed = true;
                }
                insn = next;
            }
        }
        if (!changed) {
            return basicClass;
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }
}

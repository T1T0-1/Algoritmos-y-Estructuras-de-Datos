package pe.edu.utp.videoclip;

/*
 * ============================================================
 * ARCHIVO PRINCIPAL QUE DEBE MODIFICAR EL ESTUDIANTE
 * ============================================================
 */
public final class StudentWork {

    private StudentWork() {}

    // TODO 1
    public static double calculateAudioLevel(
            short[] samples,
            int start,
            int end
    ) {
        if (start >= end) return 0.0;
        
        double suma = 0;
        for (int i = start; i < end; i++) {
            suma += Math.abs(samples[i]);
        }
        
        double promedio = suma / (end - start);
        return Math.min(promedio / 32768.0, 1.0); 
    }
    
    // TODO 2
    public static int chooseImageIndex(
            double level,
            int frameNumber,
            int totalFrames,
            int imageCount
    ) {
        if (imageCount <= 1) return 0;

        // Reparte el tiempo del video equitativamente entre todas las imágenes
        // Así nos aseguramos de que el programa cargue y muestre las 4.
        int framesPerImage = totalFrames / imageCount;
        if (framesPerImage == 0) framesPerImage = 1;
        
        int index = frameNumber / framesPerImage;
        
        return Math.min(index, imageCount - 1);
    }

    // TODO 3

    public static MatrixImage applyEffects(
            MatrixImage base,
            double level,
            int frameNumber,
            int totalFrames
    ) {
        // Rotación base: movimiento muy lento y suave como si flotara
        double angle = Math.sin(frameNumber * 0.05) * 10.0;

        // Efecto "Glitch de Neón" según la música
        if (level > 0.70) {
            // GOLPE FUERTE: Salto brusco de 45 grados y filtro Sobel para que parezca un rayo/neón
            angle += 45.0; 
            return base.rotate(angle).sobel().brighten(1.5);
        } else if (level > 0.40) {
            // AUDIO MEDIO: La cámara "vibra" usando un desenfoque (blur)
            return base.rotate(angle).blur().brighten(1.2);
        } else {
            // AUDIO BAJO: Imagen súper nítida y ligeramente oscura para contrastar
            return base.rotate(angle).sharpen().brighten(0.8);
        }
    }
    }
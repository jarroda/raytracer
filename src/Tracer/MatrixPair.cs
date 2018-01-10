using System;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public class MatrixPair
    {
        private Matrix<double> _origin;
        private Matrix<double> _inverse;

        public MatrixPair()
        {
            _origin = Matrix.Create();
            _inverse = Matrix.Create();
        }

        public MatrixPair(Matrix<double> origin, Matrix<double> inverse)
        {
            _origin = origin;
            _inverse = inverse;
        }

        public void Translate(double tx, double ty, double tz)
            => Apply(m => m.Translate(tx, ty, tz));

        public void Scale(double sx, double sy, double sz)
            => Apply(m => m.Scale(sx, sy, sz));

        public void RotateSC(double s, double c, double x, double y, double z)
            => Apply(m => m.RotateSC(s, c, x, y, z));

        public void Rotate(double theta, double x, double y, double z)
            => Apply(m => m.Rotate(theta, x, y, z));

        public void RotateDeg(double deg, double x, double y, double z)
            => Apply(m => m.RotateDeg(deg, x, y, z));

        public void ReflectXY()
            => Apply(m => m.ReflectXY());

        public void ReflectXZ()
            => Apply(m => m.ReflectXZ());

        public void ReflectYZ()
            => Apply(m => m.ReflectYZ());

        public void ReflectOrigin()
            => Apply(m => m.ReflectOrigin());

        public void ShearX(double y, double z)
            => Apply(m => m.ShearX(y, z));

        public void ShearY(double x, double z)
            => Apply(m => m.ShearY(x, z));

        public void ShearZ(double x, double y)
            => Apply(m => m.ShearZ(x, y));

        public Vector<double> InverseImage(Vector<double> vector)
            => Inverse.Image(vector);

        public Vector<double> TransformNormal(Vector<double> vector)
        {
            var result = Vector<double>.Build.Dense(3);

            for (var i = 0; i < 3; i++)
            {
                result[i] = 0;

                for (var j = 0; j < 3; j++)
                {
                    result[i] += Inverse[j,i] * vector[j];
                }                
            }

            return result.Normalize();
        }

        private void Apply(Func<Matrix<double>, Matrix<double>> fun)
        {
            _origin = fun(_origin);
            _inverse = fun(_inverse);
        }

        public Matrix<double> Origin => _origin;
        public Matrix<double> Inverse => _inverse;

        public static MatrixPair Multiply(MatrixPair m1, MatrixPair m2)
        {
            return new MatrixPair(
                Matrix.Multiply(m1.Origin, m2.Origin),
                Matrix.Multiply(m1.Inverse, m2.Inverse)
            );
        }
    }
}
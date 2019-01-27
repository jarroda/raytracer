using System;
using System.Numerics;
using Xunit;

namespace Tracer.Tests
{
    public class UnitTest1
    {
        [Fact]
        public void Test1()
        {
            var m1 = GetTestMatrix();
            var m2 = GetTestMatrixOld();

            // Console.WriteLine(m1);
            // Console.WriteLine(m2);

            AssertMatrixEqual(m1, m2);
        }

        [Fact]
        public void Test3()
        {
            var v1 = new Vector3(1, 2, 3);
            var f = 1.4f;
            
            Assert.Equal(v1 * f, Vector3.Multiply(v1, f));
        }

        // [Fact]
        // public void Test2()
        // {
        //     var m1 = GetTestMatrix();
        //     var m2 = GetTestMatrixOld();

        //     var v1 = m1.Image(new Vector3(1, 2, 3));
        //     var v2 = m2.Image(Vector.CreateVector3(1, 2, 3));

        //     Console.WriteLine(v1);
        //     Console.WriteLine(v2);

        //     AssertVectorEqual(v1, v2);
        // }

        public void AssertMatrixEqual(Matrix4x4 m1, MathNet.Numerics.LinearAlgebra.Matrix<double> m2)
        {
            Assert.Equal(m1.M11, m2[0,0]);
            Assert.Equal(m1.M12, m2[0,1]);
            Assert.Equal(m1.M13, m2[0,2]);
            Assert.Equal(m1.M14, m2[0,3]);
            Assert.Equal(m1.M21, m2[1,0]);
            Assert.Equal(m1.M22, m2[1,1]);
            Assert.Equal(m1.M23, m2[1,2]);
            Assert.Equal(m1.M24, m2[1,3]);
            Assert.Equal(m1.M31, m2[2,0]);
            Assert.Equal(m1.M32, m2[2,1]);
            Assert.Equal(m1.M33, m2[2,2]);
            Assert.Equal(m1.M34, m2[2,3]);
            Assert.Equal(m1.M41, m2[3,0]);
            Assert.Equal(m1.M42, m2[3,1]);
            Assert.Equal(m1.M43, m2[3,2]);
            Assert.Equal(m1.M44, m2[3,3]);
        }

        public void AssertVectorEqual(Vector3 v1, MathNet.Numerics.LinearAlgebra.Vector<double> v2)
        {
            Assert.Equal(v1.X, v2[0]);
            Assert.Equal(v1.Y, v2[1]);
            Assert.Equal(v1.Z, v2[2]);
        }

        private Matrix4x4 GetTestMatrix()
            => new Matrix4x4(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

        private MathNet.Numerics.LinearAlgebra.Matrix<double> GetTestMatrixOld()
        {
            var m = Matrix.Create();
            
            int count = 0;
            for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
            {
                m[i,j] = count;
                count++;
            }

            return m;
        }
    }

    public static class Ext
    {
        public static void Dump(this Matrix4x4 m)
        {
            
        }
    }
}

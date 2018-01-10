using System;
using System.Numerics;
using Xunit;

namespace Tracer.Tests
{
    public class UnitTest1
    {
        // [Fact]
        public void Test1()
        {
            
            //Matrix4x4 m = GetTestMatrix();

            Matrix4x4.Invert(GetTestMatrix(), out Matrix4x4 m);
            m.DumpEx();

        }

        [Fact]
        public void TranslateMatrix()
        {
            var m = GetTestMatrix();

            var t = Matrix4x4.CreateTranslation(1, 2, 3);

m.Translation = new Vector3(1, 2, 3);

            //Matrix4x4.Multiply(m, t).DumpEx();
            m.DumpEx();
        }

        private Matrix4x4 GetTestMatrix()
            => new Matrix4x4(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
    }
}
